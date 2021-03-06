/**
 * Copyright © 2012 Alcatel-Lucent.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * Licensed to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.alu.e3.gateway.common.camel.processor;

import java.util.UUID;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.alu.e3.common.E3Constant;
import com.alu.e3.tdr.TDRConstant;
import com.alu.e3.tdr.TDRDataService;

public class LogCorrelationIDGenerator implements Processor {
	@Override
	public void process(Exchange exchange) throws Exception {
		long LogCorrelationIDTimestamp;
		if(TDRDataService.getTxTDRProperty(TDRConstant.CLIENT_REQ_TIME, exchange) != null){
			LogCorrelationIDTimestamp = (Long) TDRDataService.getTxTDRProperty(TDRConstant.CLIENT_REQ_TIME, exchange);
		}else{
			LogCorrelationIDTimestamp = System.currentTimeMillis();
		}
		
		String correlationid = LogCorrelationIDTimestamp + "_" + UUID.randomUUID().toString();
		exchange.getIn().setHeader(E3Constant.CORRELATION_ID_HEADER_NAME, correlationid);
		
		TDRDataService.setTxTDRProperty(TDRConstant.CORRELATION, correlationid, exchange);
	}
}
