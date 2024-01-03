// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

contract FileRegistry {
    struct File {
        uint256 id;          // 文件唯一编号
        string name;
        // 文件的类型，如pdf
        string fileType;
        string remarks;
        address uploader;
        string ipfsHash;
        //辅助文件类型
        string systemCategory;
        string systemHash;
    }

    File[] private  files;
    mapping(string => uint256[]) private systemFiles;
    uint256 public fileCount = 0; // 文件计数器

    // 上传文件
    function uploadFile(
        string memory name,
        string memory fileType,
        string memory remarks,
        string memory ipfsHash,
        string memory systemCategory,
        string memory systemHash
    ) public {
        fileCount++; // 文件编号递增
        files.push(File({
            id: fileCount,
            name: name,
            fileType: fileType,
            remarks: remarks,
            uploader: msg.sender,
            ipfsHash: ipfsHash,
            systemCategory: systemCategory,
            systemHash: systemHash
        }));

        systemFiles[systemHash].push(fileCount - 1);
    }

    // 根据系统hash获取文件
    function getFilesBySystemHash(string memory systemHash) public view returns (File[] memory) {
        uint256[] storage indices = systemFiles[systemHash];
        File[] memory systemFilesArray = new File[](indices.length);

        for (uint256 i = 0; i < indices.length; i++) {
            systemFilesArray[i] = files[indices[i]];
        }

        return systemFilesArray;
    }
}
