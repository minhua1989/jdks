package com.xf.jdks.commons.componet;

import com.xf.jdks.commons.global.FileInfoPo;
import com.xf.jdks.commons.global.FileTypePo;
import com.xf.jdks.commons.util.DBUtils;
import com.xf.jdks.commons.util.Format;
import com.xf.jdks.dao.bean.Parameter;
import com.xf.jdks.dao.bean.QueryParams;
import com.xf.jdks.exceptions.DataNotFoundException;
import com.xf.jdks.exceptions.FileTypeNotFoundException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * Created by riolee on 16-8-4.
 * 文件处理服务组件：
 *  -用于处理所有文件的输入输出
 */
@Component
public class FileIOComponent {
    public static final int PHOTO_TYPE = 1;//照片
    public static final int ZUOZHENG_TYPE = 2;//佐证
    public static final int SEAL_TYPE =3;//印章
    public static final int IMPORT_TYPE = 4;//导入文件
    public static final int EXPORT_TYPE = 5;//导出文件
    public static final int SPZ_TYPE = 7;//生僻字文件


    @Autowired
    private BaseDaoComponent baseDaoComponent;

    private static List<FileTypePo> fileTypePoList = new ArrayList<>();

    /**
     * 加载文件类型结构
     */
    public void reloadFileTypes() throws SQLException {
        QueryParams viewFileTypeInfo = QueryParams.createQueryParams("vb_getfiletypeinfo");
        List<Map<String,Object>> dataList = baseDaoComponent.selectDataByParams(viewFileTypeInfo);
        fileTypePoList.clear();
        fileTypePoList = FileTypePo.createFileTypePosByList(dataList);
    }

    /**
     * 根据文件类型ID获取临时文件对象
     * @param type 文件类型
     * @param suix 后缀名（不要带点）
     * @return 相应的文件对象
     * @throws FileTypeNotFoundException 当文件类型不支持时报出此异常
     */
    public static FileInfoPo createTempFilePathByType(int type, String suix) throws FileTypeNotFoundException {
        String name = UUID.randomUUID().toString()+"."+suix;
        FileInfoPo rs = new FileInfoPo(type);
        rs.setName(name);
        return rs;
    }
    /**
     * 根据文件类型ID获取临时文件对象
     * @param type 文件类型
     * @param suix 后缀名（不要带点）
     * @param fileName 文件名称
     * @return 相应的文件对象
     * @throws FileTypeNotFoundException 当文件类型不支持时报出此异常
     */
    public static FileInfoPo createTempFilePathByType(int type, String suix,String fileName) throws FileTypeNotFoundException {
        String name = fileName +"_"+ Format.getDateTimeLong()+"."+suix;
        FileInfoPo rs = new FileInfoPo(type);
        rs.setName(name);
        return rs;
    }

    public static FileInfoPo createTempFileForNameAndType(int type,String name) throws FileTypeNotFoundException {
        FileInfoPo rs = new FileInfoPo(type);
        rs.setName(name);
        return rs;
    }


    /**
     * 根据文件类型ID获取对应文件类型对象
     * @param type 文件类型ID
     * @return 文件类型对象
     * @throws FileTypeNotFoundException 当文件类型不支持时报出此异常
     */
    public static FileTypePo searchFileTypeByTypeId(int type) throws  FileTypeNotFoundException {
        for(FileTypePo fileType : fileTypePoList){
            if(fileType.getId() == type)return fileType;
        }
        throw new FileTypeNotFoundException();
    }

    /**
     * @param id 根据ID查询文件
     * @return 文件对象
     * @throws SQLException
     * @throws DataNotFoundException
     * @throws FileTypeNotFoundException
     */
    public FileInfoPo queryFileById(String id) throws SQLException, DataNotFoundException, FileTypeNotFoundException {
        QueryParams fileInfo = QueryParams.createQueryParams("vb_getfileinfo");
        fileInfo.addQueryParams(Parameter.createParameter("id",id));
        List<Map<String,Object>> fileData = baseDaoComponent.selectDataByParams(fileInfo);
        if(fileData==null||fileData.size()==0)throw new DataNotFoundException();
        Map<String,Object> data = fileData.get(0);
        FileInfoPo rs = FileInfoPo.createFileInfoByMapData(data);
        return rs;
    }

    /**
     * @param id 根据ID查询文件
     * @return 文件对象的二进制数据
     * @throws SQLException
     * @throws DataNotFoundException
     * @throws FileTypeNotFoundException
     * @throws IOException
     */
    public byte[] queryFileByIdAsBlob(String id) throws SQLException, DataNotFoundException, FileTypeNotFoundException, IOException {
        FileInfoPo infoPo = queryFileById(id);
        File file = infoPo.getFileObject();
        return FileUtils.readFileToByteArray(file);
    }

    public FileInfoPo addNewFileByBlobForSuix(Blob file,int type,String suix) throws FileTypeNotFoundException, IOException, SQLException {
        String name = UUID.randomUUID().toString()+"."+suix;
        return addNewFileByBlob(file,type,name);
    }

    /**
     * @param file 文件数据
     * @param type 文件类型
     * @param name 文件名
     * @return
     * @throws FileTypeNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public FileInfoPo addNewFileByBlob(Blob file,int type,String name) throws FileTypeNotFoundException, IOException, SQLException {
        FileInfoPo infoPo = new FileInfoPo(type);
        infoPo.setName(name);
        File infoFile = infoPo.getFileObject();
        FileUtils.copyInputStreamToFile(file.getBinaryStream(),infoFile);
        baseDaoComponent.insertDataByParams(infoPo.getCurrentInsertParams());
        return infoPo;
    }

    /**
     * 根据类型处理上传上来的文件
     * @param file 上传上来的文件
     * @param type 文件类型ID
     * @param name 文件的名称
     * @return 已经处理过的文件对象
     * @throws FileTypeNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public FileInfoPo addNewFileByUpload(MultipartFile file,int type,String name) throws FileTypeNotFoundException, IOException, SQLException {
        //创建一个新的上传文件
        FileInfoPo infoPo = new FileInfoPo(type);
        infoPo.setName(name);
        File infoFile = infoPo.getFileObject();
        FileUtils.copyInputStreamToFile(file.getInputStream(),infoFile);
        //写入文件记录
        baseDaoComponent.insertDataByParams(infoPo.getCurrentInsertParams());
        //put in file sb info
        return infoPo;
    }

//    public void pushFileByFileId(String fileId,String ywid,int type) throws Exception {
//        FileInfoPo fio = queryFileById(fileId);
//        if(fio!=null) {
//            File file = fio.getFileObject();
//            byte[] bytes = FileUtils.readFileToByteArray(file);
//            String id = UUID.randomUUID().toString();
//            Connection conn = DBUtils.GetConnectionOracleMaster();
//            String sql = "insert into t_file_sb_info (id,ywid,fileid,ywtype,fj) values ('"+id+"','"+ywid+"','"+fileId+"','"+type+"',?)";
//            PreparedStatement pre = conn.prepareStatement(sql);
//            pre.setBytes(1,bytes);
//            pre.execute();
//            pre.close();
//            conn.close();
//            return;
//        }
//        throw new FileNotFoundException();
//    }


   public FileInfoPo addNewFileByUploadForSuix(MultipartFile file,int type,String suix) throws FileTypeNotFoundException, IOException, SQLException {
       return addNewFileByUpload(file,type,UUID.randomUUID().toString()+"."+suix);
   }

   public FileInfoPo addNewFileByUploadForSuix(MultipartFile file,int type,String suix,String filename) throws FileTypeNotFoundException, IOException, SQLException {
       return addNewFileByUpload(file,type,filename+"_"+Format.getDateTimeLong()+"."+suix);
   }

}
