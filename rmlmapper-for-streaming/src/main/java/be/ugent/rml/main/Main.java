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

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;

/**
 * Extension of RMLMapper for rmlmapper-for-streaming
 *   Development based on rmlmapper-java 0.0.5
 *   Replaces be.ugent.rml.cli
 * @author      Mihary Ranaivoson
 * @since       0.1.0
 */
public class Main
{

    public static void main(String[] args)
    {
        try {
            // Parse command Line
            CommandLine lineArgs = getCommandLineArgs(args);

            // record
            String record_str = lineArgs.getOptionValue('r');

            // mappingFilePath
            String mappingFilePath = lineArgs.getOptionValue('m');

            // outputFormat
            String outputFormat = "turtle";
            if(lineArgs.hasOption('s')) outputFormat = lineArgs.getOptionValue('s');

            // triplesMaps
            String triplesMaps_str = "";
            if(lineArgs.hasOption('t')) triplesMaps_str = lineArgs.getOptionValue('t');

            // Mapping
            JSONRecordMapper noriaRMLMapper = new JSONRecordMapper(mappingFilePath, outputFormat, triplesMaps_str);
            String res = noriaRMLMapper.map(record_str);

            // Display
            System.out.println(res);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static CommandLine getCommandLineArgs(String[] args) throws ParseException
    {
        Option opt_r = Option.builder("r")
                .required(true)
                .desc("Record to map (ie data to map")
                .longOpt("record")
                .hasArg(true)
                .build();

        Option opt_m = Option.builder("m")
                .required(true)
                .desc("Path of mapping rules")
                .longOpt("mappingfile")
                .hasArg(true)
                .build();

        Option opt_s = Option.builder("s")
                .required(false)
                .desc("Serialization format (nquads, turtle, trig, trix, jsonld, hdt) | default: turtle")
                .longOpt("serialization")
                .hasArg(true)
                .build();

        Option opt_t = Option.builder("t")
                .required(false)
                .desc("IRIs of the triplesmaps that should be executed in order, split by ',' | default: all triplesmaps)")
                .longOpt("triplesmaps")
                .hasArg(true)
                .build();

        Options options = new Options();
        options.addOption(opt_r);
        options.addOption(opt_m);
        options.addOption(opt_s);
        options.addOption(opt_t);

        return (new DefaultParser()).parse(options, args);
    }



    public static void main(String[] args, String basePath)
    {
        // just for compatibility
    }
}
