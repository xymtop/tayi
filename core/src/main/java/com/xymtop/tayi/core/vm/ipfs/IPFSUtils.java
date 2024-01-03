package com.xymtop.tayi.core.vm.ipfs;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/27 13:43
 */
import cn.hutool.core.io.FileUtil;
import com.xymtop.tayi.core.utils.fileutils.FileUtils;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;


@Component
public class IPFSUtils {

    @Autowired
    private  IPFS ipfs;

    public String uploadFile(String filePath) throws IOException {
        filePath = FileUtils.getResourcesFilePath(filePath);
        NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(new File(filePath));
        MerkleNode response = ipfs.add(file).get(0);
        return response.hash.toString();
    }

    public byte[] downloadFile(String hash) throws IOException {
        return ipfs.cat(Multihash.fromBase58(hash));
    }

    //直接从哈希保存文件到本地
    public boolean downloadFile(String hash,String filePath) throws IOException {
        filePath = FileUtils.getResourcesFilePath(filePath);
        byte[] bytes = downloadFile(hash);
        if (bytes == null){
            return false;
        }
        //bytes写入到文件
        FileUtil.writeBytes(bytes,new File(filePath));

        return FileUtil.exist(filePath);
    }

    //判断文件或者路径是否存在
    public  boolean isExist(String filePath) throws IOException {
        File file = new File(FileUtils.getResourcesFilePath(filePath));
        return file.exists();
    }
}
