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

package com.orange.noria.dsm.mains;

import com.orange.noria.dsm.components.MyKafkaConsumer;
import com.orange.noria.dsm.components.MyKafkaProducer;
import com.orange.noria.dsm.components.MyPipeline;

import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;

/**
 * StreamingMASSIF pipeline model
 * @author      Mihary Ranaivoson
 * @since       0.1.0
 */
public class MainBase
{
    // Pipeline
    private String mappingFilePath;
    private String outputFormat;
    private String triplesMaps;

    // Producer
    private String producerKafkaServer;
    private String producerKafkaTopic;
    private String producerBashColor;

    // Consumer
    private String consumerKafkaServer;
    private String consumerKafkaTopic;
    private String consumerBashColor;


    public MainBase(String config_file_path, String section) throws IOException
    {
        Ini ini = new Ini(new File(config_file_path));

        // pipeline
        this.mappingFilePath = ini.get(section, "mapping_file_path");
        this.outputFormat = ini.get(section, "output_format");
        this.triplesMaps = ini.get(section, "triples_maps");

        // producer
        this.producerKafkaServer = ini.get(section, "producer_kafka_server");
        this.producerKafkaTopic = ini.get(section, "producer_kafka_topic");
        this.producerBashColor = "" + ini.get(section, "producer_bash_color");

        // consumer
        this.consumerKafkaServer = ini.get(section, "consumer_kafka_server");
        this.consumerKafkaTopic = ini.get(section, "consumer_kafka_topic");
        this.consumerBashColor = "" + ini.get(section, "consumer_bash_color");
    }


    public void run()
    {
        // Pipeline
        MyPipeline pipeline = new MyPipeline(this.producerKafkaServer, this.producerKafkaTopic, this.consumerKafkaServer, this.consumerKafkaTopic, this.mappingFilePath, this.outputFormat, this.triplesMaps);
        Thread pipelineThread = new Thread(pipeline);
        pipelineThread.start();

        // Producer
        MyKafkaProducer producer = new MyKafkaProducer(this.producerKafkaServer, this.producerKafkaTopic, this.producerBashColor);
        Thread producerThread = new Thread(producer);
        producerThread.start();

        // Consumer
        MyKafkaConsumer consumer = new MyKafkaConsumer(this.consumerKafkaServer, this.consumerKafkaTopic, this.consumerBashColor);
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();
    }

}
