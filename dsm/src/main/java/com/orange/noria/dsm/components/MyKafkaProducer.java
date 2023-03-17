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

import com.github.javafaker.Faker;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Properties;

/**
 * Kafka producer with random data generator in JSON syntax
 * @author      Mihary Ranaivoson
 * @since       0.1.0
 */
public class MyKafkaProducer implements Runnable
{
    private String kafkaServer;
    private String kafkaTopic;
    private String color;
    private Producer producer;
    private Faker faker;

    public MyKafkaProducer(String kafkaServer, String kafkaTopic)
    {
        this(kafkaServer, kafkaTopic, "\033[35m");
    }

    public MyKafkaProducer(String kafkaServer, String kafkaTopic, String color)
    {
        this.kafkaServer = kafkaServer;
        this.kafkaTopic = kafkaTopic;
        this.color = color;

        Properties props = new Properties();
        props.put("bootstrap.servers", this.kafkaServer);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        this.producer = new KafkaProducer(props);

        this.faker = new Faker();
    }

    @Override
    public void run()
    {
        this.display("Producer is starting");
        int ite = 1;
        while(true)
        {
            JSONObject jsonRecord = generateJSONRecord(ite);
            ProducerRecord procucerRecord = new ProducerRecord(this.kafkaTopic, jsonRecord.toString());
            this.producer.send(procucerRecord);
            this.display("\n" + jsonRecord.toString(4) + "\n");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ite++;
        }
    }


    public JSONObject generateJSONRecord(int ite)
    {
        // ini
        JSONObject jsonRecord = new JSONObject();

        // id
        jsonRecord.put("id", ite);

        // name
        JSONObject jsonName = new JSONObject();
        jsonName.put("family_name", this.faker.name().lastName());
        jsonName.put("first_name", this.faker.name().firstName());
        jsonRecord.put("name", jsonName);

        // friends
        int minFriends = 0;
        int maxFriends = 5;
        int nbFriends = minFriends + (int)(Math.random() * ((maxFriends - minFriends) + 1));
        JSONArray friends = new JSONArray();
        for(int i=0; i<nbFriends; i++) friends.put(this.faker.name().firstName());
        jsonRecord.put("friends", friends);

        // enemies
        int minEnemies = 0;
        int maxEnemies = 5;
        int nbEnemies = minEnemies + (int)(Math.random() * ((maxEnemies - minEnemies) + 1));
        JSONArray enemies = new JSONArray();
        for(int i=0; i<nbEnemies; i++) enemies.put(this.faker.name().firstName());
        jsonRecord.put("enemies", enemies);

        // return
        return jsonRecord;
    }

    public void display(String s)
    {
        System.out.println(this.color + s + "\033[0m");
    }

}
