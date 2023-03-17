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

/**
 * Extension of RMLMapper for rmlmapper-for-streaming
 *   ToyExample for guidance
 * @author      Mihary Ranaivoson
 * @since       0.1.0
 */
public class ToyExample
{

    private static String record_str = "{"
            +  "\"id\": 1,"
            + "\"name\": {"
            + "\"first_name\": \"Alice\","
            + "\"family_name\": \"agrougrou\""
            + "},"
            + "\"friends\": [ \"Bob\", \"Carl\" ],"
            + "\"enemies\": [ \"Eve\", \"Fanny\" ]"
            + "}" ;
    private static String mappingFilePath = "./src/test/resources/json-record-mapper/json_mapping.ttl";
    private static String outputFormat = "turtle";
    private static String triplesMaps_str = "";


    public static void main(String[] args)
    {
        try {
            JSONRecordMapper noriaRMLMapper = new JSONRecordMapper(ToyExample.mappingFilePath, ToyExample.outputFormat, ToyExample.triplesMaps_str);
            String res = noriaRMLMapper.map(ToyExample.record_str);

            System.out.println("\n-------------------\n");
            System.out.println(res);
            System.out.println("\n-------------------\n");
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

}
