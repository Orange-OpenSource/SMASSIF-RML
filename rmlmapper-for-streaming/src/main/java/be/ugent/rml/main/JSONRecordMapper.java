/*
 * Copyright (c) 2022-2023 Orange. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *     1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *     3. All advertising materials mentioning features or use of this software must display the following acknowledgement:
 *     This product includes software developed by Orange.
 *     4. Neither the name of Orange nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY Orange "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL Orange BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package be.ugent.rml.main;

import be.ugent.rml.Executor;
import be.ugent.rml.Utils;
import be.ugent.rml.functions.FunctionLoader;
import be.ugent.rml.records.RecordsFactory;
import be.ugent.rml.store.QuadStore;
import be.ugent.rml.store.QuadStoreFactory;
import be.ugent.rml.store.RDF4JStore;
import be.ugent.rml.term.NamedNode;
import be.ugent.rml.term.Term;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Extension of RMLMapper for rmlmapper-for-streaming
 *   Development based on rmlmapper-java 0.0.5
 *   Replaces be.ugent.rml.cli
 * @author      Mihary Ranaivoson
 * @since       0.1.0
 */
public class JSONRecordMapper
{
    private String outputFormat;
    private File mappingFile;
    private List<Term> triplesMaps;
    private FunctionLoader functionLoader;


    public JSONRecordMapper(String mappingFilePath, String outputFormat, String triplesMaps_str)
    {
        try {
            // this.outputFormat
            this.outputFormat = outputFormat;

            // this.mappingFile
            this.mappingFile = new File(mappingFilePath);

            // this.functionLoader
            this.functionLoader = new FunctionLoader();

            // this.triplesMaps
            this.triplesMaps = new ArrayList<Term>();
            if ((triplesMaps_str != "") &&  (triplesMaps_str != null))
            {
                List<String> triplesMapsIRI = Arrays.asList(triplesMaps_str.split(","));
                triplesMapsIRI.forEach(iri -> {
                    this.triplesMaps.add(new NamedNode(iri));
                });
            }
            // TODO: fix the `java.lang.IllegalArgumentException: Not a valid (absolute) IRI` that is raised when triples_maps in config.ini is empty.

        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public String map(String record_str)
    {
        try {
            Executor executor = new Executor(
                    QuadStoreFactory.read(new FileInputStream(this.mappingFile)),
                    new RecordsFactory(mappingFile.getParent()),
                    this.functionLoader,
                    new RDF4JStore(),
                    Utils.getBaseDirectiveTurtle(new FileInputStream(mappingFile))
            );


            HashMap<Term, QuadStore> targets = executor.executeV5(
                    triplesMaps,
                    true,
                    null,
                    record_str
            );

            QuadStore result = targets.get(new NamedNode("rmlmapper://default.store"));

            StringWriter sw = new StringWriter();
            result.write(sw, outputFormat);
            return sw.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
