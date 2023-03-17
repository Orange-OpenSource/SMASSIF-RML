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

package com.orange.noria.dsm.components;

import idlab.massif.core.PipeLineComponent;
import idlab.massif.sources.KafkaSource;

//import idlab.massif.mapping.RMLMapper;
//import idlab.massif.sinks.KafkaSink;
import com.orange.noria.massif.mapping.RMLMapper;
import com.orange.noria.massif.sinks.KafkaSink;

import java.util.Collections;

/**
 * StreamingMASSIF pipeline implementation, with following components:
 *   kafkaSource <= Kafka producer with random data generator in JSON syntax
 *   rmlMapper <= rmlmapper-for-streaming
 *   kafkaSink <= Kafka consumer displaying JSON data
 * @author      Mihary Ranaivoson
 * @since       0.1.0
 */
public class MyPipeline implements Runnable
{
    private String producerKafkaServer;
    private String producerKafkaTopic;
    private String consumerKafkaServer;
    private String consumerKafkaTopic;
    private String mappingFilePath;
    private String outputFormat;
    private String triplesMaps;


    public MyPipeline(String producerKafkaServer, String producerKafkaTopic, String consumerKafkaServer, String consumerKafkaTopic, String mappingFilePath, String outputFormat, String triplesMaps)
    {
        this.producerKafkaServer = producerKafkaServer;
        this.producerKafkaTopic = producerKafkaTopic;
        this.consumerKafkaServer = consumerKafkaServer;
        this.consumerKafkaTopic = consumerKafkaTopic;
        this.mappingFilePath = mappingFilePath;
        this.outputFormat = outputFormat;
        this.triplesMaps = triplesMaps;
    }

    @Override
    public void run()
    {
        System.out.println("Pipeline is starting (press Ctrl-C to stop) ...");
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        System.out.println("Press Ctrl-C for stopping the pipeline.");

        // All PipeLineElement
        KafkaSource kafkaSource = new KafkaSource(this.producerKafkaServer, this.producerKafkaTopic);
        RMLMapper rmlMapper = new RMLMapper(this.mappingFilePath, this.outputFormat, this.triplesMaps);
        KafkaSink kafkaSink = new KafkaSink(this.consumerKafkaServer, this.consumerKafkaTopic);

        // All PipeLineComponent
        PipeLineComponent senderComp = new PipeLineComponent(kafkaSink, Collections.EMPTY_LIST);
        PipeLineComponent mapperComp = new PipeLineComponent(rmlMapper, Collections.singletonList(senderComp));
        PipeLineComponent sourceComp = new PipeLineComponent(kafkaSource, Collections.singletonList(mapperComp));

        // Run
        kafkaSource.stream();
    }

}
