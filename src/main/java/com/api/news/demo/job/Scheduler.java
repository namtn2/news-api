//package com.api.news.demo.job;
//
//import com.api.news.demo.model.LogModel;
//import com.api.news.demo.repository.MongoExtendRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import java.io.File;
//import java.io.PrintWriter;
//import java.nio.charset.Charset;
//import java.nio.file.FileSystems;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@Component
//public class Scheduler {
//
//    @Value("${app.file.log.name:null}")
//    private String fileName;
//
//    @Autowired
//    MongoExtendRepository mongoRepository;
//
//    @Scheduled(fixedRate = 60 * 60 * 1000)
//    public void clearLogAndSaveToMongo() {
//        Path pathFolder = FileSystems.getDefault().getPath(new String("./")).toAbsolutePath().getParent();
//        File f = new File(pathFolder + "\\" + fileName);
//        String value = "";
//        if (f.exists()) {
//            try {
//                byte[] encoded = Files.readAllBytes(Paths.get(pathFolder + "\\" + fileName));
//                value = new String(encoded, Charset.defaultCharset());
//                PrintWriter writer = new PrintWriter(f);
//                writer.print("");
//                writer.close();
//
//                LogModel log = new LogModel();
//                log.setContent(value);
//                mongoRepository.save(log);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
