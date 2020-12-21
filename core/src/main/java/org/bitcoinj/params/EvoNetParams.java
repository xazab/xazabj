/*
 * Copyright 2019 Dash Core Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bitcoinj.params;

public class EvoNetParams extends DevNetParams {

    /*
        connection parameters:
        https://dashplatform.readme.io/docs/tutorial-connecting-to-evonet-dash-core-full-node
     */
    public static String DEVNET_NAME = "evonet-8";
    public static String[] MASTERNODES = {
            "54.188.72.112",
            "18.236.235.220",
            "54.190.1.129",
            "52.88.52.65",
            "54.189.121.60",
            "34.219.43.9",
            "54.69.71.240",
            "34.219.79.193",
            "54.184.89.215",
            "54.189.73.226",
            "52.88.13.87",
            "34.220.159.57",
            "34.220.38.59",
            "34.221.226.198",
            "54.190.26.250",
            "54.202.214.68",
            "34.222.91.196",
            "54.149.99.26",
            "54.186.22.30",
            "54.190.136.191",
            "34.221.185.231",
            "52.33.251.111",
            "35.167.226.182",
            "54.184.71.154",
            "35.164.4.147",
            "54.186.133.94",
            "54.203.2.102",
            "34.216.133.190",
            "54.212.206.131",
            "34.221.5.65",
            "54.244.159.60",
            "52.25.73.91",
            "54.186.129.244",
            "52.32.251.203",
            "34.212.169.216",
            "211.30.243.82",
            "18.237.194.30",
            "54.244.203.43",
            "54.200.73.105",
            "54.149.181.16",
            "54.187.128.127",
            "54.186.145.12",
            "18.237.255.133",
            "18.236.73.143",
            "54.245.217.116",
            "34.214.12.133",
            "54.185.186.111",
            "52.88.38.138",
            "18.236.139.199",
            "34.223.226.20",
            "35.167.241.7"
    };

    public EvoNetParams() {
        super(DEVNET_NAME, "yQuAu9YAMt4yEiXBeDp3q5bKpo7jsC2eEj", 20001, MASTERNODES, true, 70215);

        dnsSeeds = new String[]{
                "seed-1.evonet.networks.xazab.xyz",
                "seed-2.evonet.networks.xazab.xyz",
                "seed-3.evonet.networks.xazab.xyz",
                "seed-4.evonet.networks.xazab.xyz",
                "seed-5.evonet.networks.xazab.xyz"
        };
    }

    private static EvoNetParams instance;

    public static EvoNetParams get() {
        if (instance == null) {
            instance = new EvoNetParams();
            add(instance);
        }
        return instance;
    }

    @Override
    public String[] getDefaultMasternodeList() {
        return MASTERNODES;
    }
}
