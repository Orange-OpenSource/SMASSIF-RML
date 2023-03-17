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

package com.orange.noria.massif.mapping;

import be.ugent.rml.main.JSONRecordMapper;
import idlab.massif.interfaces.core.ListenerInf;
import idlab.massif.interfaces.core.MapperInf;

/**
 * StreamingMASSIF pipelineElement for mapping a data stream using RML rules
 * @author      Mihary Ranaivoson
 * @since       0.1.0
 */
public class RMLMapper implements MapperInf
{
    private JSONRecordMapper jsonRecordMapper ;
    private ListenerInf listener;
    private int i = 0;


    public RMLMapper(String mappingFilePath, String outputFormat, String triplesMaps_str)
    {
        this.jsonRecordMapper = new JSONRecordMapper(mappingFilePath, outputFormat, triplesMaps_str);
    }


    @Override
    public boolean addEvent(String event)
    {
        String mappedResult = this.jsonRecordMapper.map(event);
        if (listener != null) {
            listener.notify(0, mappedResult);
        }
        return false;
    }


    @Override
    public boolean addListener(ListenerInf listener)
    {
        this.listener = listener;
        return true;
    }


    @Override
    public void start() {

    }


    @Override
    public void stop() {

    }

}
