# Copyright (c) 2022-2023 Orange. All rights reserved.
#
# Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
#     1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
#     2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
#     3. All advertising materials mentioning features or use of this software must display the following acknowledgement:
#     This product includes software developed by Orange.
#     4. Neither the name of Orange nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
#
# THIS SOFTWARE IS PROVIDED BY Orange "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL Orange BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

# =============================================================================

## makefile for the SMASSIF-RML project

# === General ==================================================================

ROOT_DIR := $(PWD)

# Loading (optional) environment variables from file.
-include ./.env

CURL_DIRECT := curl
CURL_PROXY := curl -x $(PROXY_URL)

help:	## Show this help.
	@fgrep -h "## " $(MAKEFILE_LIST) | fgrep -v fgrep | sed 's/\\$$//' | sed 's/## //'

RMLMAPPER_JAR_PATH		= "${ROOT_DIR}/rmlmapper-for-streaming/target/rmlmapper-for-streaming-0.1.0-all.jar"
RECORD					= "{ \"id\": \"Alice\", \"name\": { \"first_name\": \"Alice\", \"family_name\": \"Agougrou\" }, \"friends\": [ \"Bob\", \"Carl\" ], \"enemies\": [ \"Eve\", \"Fanny\" ] }"
MAPPING_PATH			= ${ROOT_DIR}/demo/rmlmapper-rml-demo.ttl
TRIPLEMAPS				=
SERIALIZATION			=turtle

DSM_JAR_PATH			= "${ROOT_DIR}/dsm/target/dsm-0.1.0-all.jar"
DSM_CONFIG_PATH			= "${ROOT_DIR}/dsm/src/main/resources/config.ini"

RMLMAPPER_DOCKER		=time docker run --rm -v $$(pwd):/data rmlio/rmlmapper-java rmlmapper -v -s ${SERIALIZATION}

# === BUILD ====================================================================


install-dependencies:	## Install dependencies
	@echo "\033[35m > Install StreamingMASSIF  \033[0m (see https://github.com/IBCNServices/StreamingMASSIF)"
	git clone https://github.com/IBCNServices/StreamingMASSIF.git ${ROOT_DIR}/lib/StreamingMASSIF
	mvn install -Dmaven.test.skip=true -f ${ROOT_DIR}/lib/StreamingMASSIF
	rm -rf ${ROOT_DIR}/lib/StreamingMASSIF
	@echo "\033[35m > Done  \033[0m"


# === DEMO =====================================================================

start-kafka:	## DEMO - start a Docker bitnami kafka instance on localhost
	@echo "\033[35m > Start a Docker bitnami kafka instance on localhost with docker-compose \033[0m"
	@docker-compose -p SMASSIF-RML -f ./demo/docker-compose.yml up -d
	@echo "\033[35m > Done  \033[0m"

stop-kafka:	## DEMO - stop the Kafka instance on localhost
	@echo "\033[35m > Stop the Kafka instance on localhost  \033[0m"
	docker-compose -p SMASSIF-RML -f ./demo/docker-compose.yml down
	@echo "\033[35m > Done  \033[0m"

# === TEST ====================================================================

demo-rmlmapper-for-streaming:  ## DEMO - Call compiled rmlmapper-for-streaming with demo data
	@echo "\033[35m > Call compiled rmlmapper-for-streaming with demo data \033[0m"
	@java -jar ${RMLMAPPER_JAR_PATH} \
		-r ${RECORD} \
		-m ${MAPPING_PATH} \
		-s ${SERIALIZATION}
	@echo "\033[35m > Done  \033[0m"

demo-dsm:  ## DEMO - Call compiled dsm with demo data
	@echo "\033[35m > Call compiled dsm with demo data \033[0m"
	@cd ${ROOT_DIR}/dsm ; java -jar ${DSM_JAR_PATH} -c ${DSM_CONFIG_PATH}
	@echo "\033[35m > Done  \033[0m"


# === TOOLS ===================================================================

check-rml:	## Check syntax of RML/ttl files
	@echo "\033[35m > Check RML/Turtle syntax  \033[0m  (TurtleValidator : https://www.npmjs.com/package/turtle-validator)"
	@find . -type f -name 'rml*.ttl' -printf "\n%f\n" -exec /usr/local/bin/ttl {} \;
	@echo -e "\033[35m > Done  \033[0m"

test-rmlmapper-std:  ## Run standard RML mapping procedure for comparison
	@echo -e "\033[35m > Call Ugent RMLMapper on demo/data.json \033[0m"
	${RMLMAPPER_DOCKER} \
	    -m ./demo/rmlmapper-rml-demo2.ttl \
	    -o demo/out.ttl
	@echo -e "\033[35m > Done  \033[0m"
