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

package com.orange.noria.massif.sinks;

import idlab.massif.interfaces.core.ListenerInf;
import idlab.massif.interfaces.core.SinkInf;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * StreamingMASSIF pipelineElement for sending a data stream to a Kafka topic
 * @author      Mihary Ranaivoson
 * @since       0.1.0
 */
public class KafkaSink implements SinkInf
{
    private String kafkaServer;
    private String kafkaTopic;
    private Producer<String, String> producer;

    public KafkaSink(String kafkaServer, String kafkaTopic)
    {
        this.kafkaServer = kafkaServer;
        this.kafkaTopic = kafkaTopic;

        Properties props = new Properties();
        props.put("bootstrap.servers", this.kafkaServer);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("acks", "all");
        this.producer = new KafkaProducer<>(props);
    }

    @Override
    public boolean addEvent(String event)
    {
        producer.send(new ProducerRecord<>(this.kafkaTopic, event));
        return true;
    }

    @Override
    public boolean addListener(ListenerInf listener) {
        return false;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
