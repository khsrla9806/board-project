package com.module.board.utils;

import java.io.File;

public class ImageUtils {
    public static String getFullPath(String storeThumbnailName) {
        String homeDir = System.getProperty("user.home");
        String uploadFolderName = "board-store"; // TODO: Properties 분리

        File uploadFolder = new File(homeDir + File.separator + uploadFolderName);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdir(); // 홈 경로에 board-store 폴더가 존재하지 않으면 만든다.
        }

        // 서버를 올려서 할 것이 아니고 Local 작동하기 때문에 OS별 Home 디렉터리에 파일 생성
        return homeDir + File.separator + uploadFolderName + File.separator + storeThumbnailName;
    }
}
