
import {GNode} from "./graphUtils";
import {TraceAblilityInfo} from "./infoTypes";

//模拟一组 TraceAblilityInfo信息，静态的
export async function mockTraceAblilityInfos() {
    const traceAblilityInfos: TraceAblilityInfo[] = [];
    const i =  1;

        const traceAblilityInfo: TraceAblilityInfo = {
            productNft: {
                id: "0x" + i,
                address: "0x" + i,
                name: "name" + i,
                description: "description" + i,
                image: "image" + i,
                external_url: "external_url" + i,
                attributes: ["attribute" + i],
            },
            traceNodesNft: [
                {
                    id: 1,
                    address: "0x" + i,
                    data: {
                        proofCenterAddress: "sas" + i,
                        proofCenterName: "proofCenterName1" + i,
                        proofCenterDescription: "proofCenterDescription" + i,
                        proofCenterImage: "proofCenterImage" + i,
                        proofCenterExternalUrl: "proofCenterExternalUrl" + i,
                        proofCenterAttributes: ["proofCenterAttribute" + i],
                        traceabilityInfo: {
                            id: "0x" + i,
                            address: "0x" + i,
                            name: "name" + i,
                            description: "description" + i,
                            image: "image" + i,
                            external_url: "external_url" + i,
                            attributes: ["attribute" + i],
                        },
                        traceabilityInfoAttributes: ["traceabilityInfoAttribute" + i],
                    },
                },
                {
                    id:  2,
                    address: "0x" + i,
                    data: {
                        proofCenterAddress: "sasasasa" + i,
                        proofCenterName: "proofCenterName2" + i,
                        proofCenterDescription: "proofCenterDescription" + i,
                        proofCenterImage: "proofCenterImage" + i,
                        proofCenterExternalUrl: "proofCenterExternalUrl" + i,
                        proofCenterAttributes: ["proofCenterAttribute" + i],
                        traceabilityInfo: {
                            id: "0x" + i,
                            address: "0x" + i,
                            name: "name" + i,
                            description: "description" + i,
                            image: "image" + i,
                            external_url: "external_url" + i,
                            attributes: ["attribute" + i],
                        },
                        traceabilityInfoAttributes: ["traceabilityInfoAttribute" + i],
                    },
                },
                {
                    id:3,
                    address: "0x" + i,
                    data: {
                        proofCenterAddress: "sasccas" + i,
                        proofCenterName: "proofCenterName3" + i,
                        proofCenterDescription: "proofCenterDescription" + i,
                        proofCenterImage: "proofCenterImage" + i,
                        proofCenterExternalUrl: "proofCenterExternalUrl" + i,
                        proofCenterAttributes: ["proofCenterAttribute" + i],
                        traceabilityInfo: {
                            id: "0x" + i,
                            address: "0x" + i,
                            name: "name" + i,
                            description: "description" + i,
                            image: "image" + i,
                            external_url: "external_url" + i,
                            attributes: ["attribute" + i],
                        },
                        traceabilityInfoAttributes: ["traceabilityInfoAttribute" + i],
                    },
                },
                {
                    id: 2,
                    address: "0x" + i,
                    data: {
                        proofCenterAddress: "sadadasda" + i,
                        proofCenterName: "proofCenterName4" + i,
                        proofCenterDescription: "proofCenterDescription" + i,
                        proofCenterImage: "proofCenterImage" + i,
                        proofCenterExternalUrl: "proofCenterExternalUrl" + i,
                        proofCenterAttributes: ["proofCenterAttribute" + i],
                        traceabilityInfo: {
                            id: "0x" + i,
                            address: "0x" + i,
                            name: "name" + i,
                            description: "description" + i,
                            image: "image" + i,
                            external_url: "external_url" + i,
                            attributes: ["attribute" + i],
                        },
                        traceabilityInfoAttributes: ["traceabilityInfoAttribute" + i],
                    },
                },

            ],
        };
        traceAblilityInfos.push(traceAblilityInfo);
    return traceAblilityInfos;
}