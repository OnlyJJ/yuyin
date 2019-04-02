package com.jiujun.voice.modules.apps.file.handle;

import java.io.File;
import java.util.Date;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.utils.DateUtils;
import com.jiujun.voice.common.utils.FileUtils;
import com.jiujun.voice.common.utils.http.HttpUtil;
import com.jiujun.voice.common.utils.http.entity.HttpEntity;

/**
 * 
 * @author Coody
 *
 */
@Component
public class FileWriteHandle {

	@Value("${file.savePath}")
	private String savePath;

	public String writeFile(String busType, String suffix, byte[] file) {
		if (file == null) {
			return null;
		}
		String fileName = getFileName(busType, suffix);
		if (!FileUtils.writeFile(savePath + File.separator + fileName, file)) {
			throw ErrorCode.ERROR_1046.builderException();
		}
		return File.separator + fileName;
	}

	public String getFileName(String busType, String suffix) {
		String uri = busType + "/" + DateUtils.toString(new Date(), "yyyyMMdd") + "/";
		StringBuffer sb = new StringBuffer(uri + DateUtils.toString(new Date(), "ddHHmmssSSS"));
		sb.append(System.currentTimeMillis() + "");
		for (int i = 0; i < 4; i++) {
			sb.append(RandomUtils.nextInt(10));
		}
		sb.append(".").append(suffix);
		return sb.toString().toLowerCase();
	}

	public static void main(String[] args) {
		FileWriteHandle fileWriteHandle = new FileWriteHandle();
		fileWriteHandle.savePath = "d://";
		HttpEntity entity = HttpUtil.get(
				"http://thirdwx.qlogo.cn/mmopen/vi_32/UfqEYrR3cQUriaherHLBIqFvjHeYhux8iabl1Y37rM4ibYAMdmtGQVjVz8ibPxW1W3dKug3fOqevdCAkgzklOoLsNA/132");
		String uri = fileWriteHandle.writeFile("head", "jpg", entity.getBye());
		System.out.println(uri);
	}
}
