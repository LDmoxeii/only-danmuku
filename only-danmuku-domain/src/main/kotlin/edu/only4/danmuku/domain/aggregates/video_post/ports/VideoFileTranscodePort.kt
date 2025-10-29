package edu.only4.danmuku.domain.aggregates.video_post.ports

import java.io.File

/**
 * 端口接口：视频文件转码相关的基础设施能力
 * 由应用/基础设施层实现，领域实体通过该端口驱动外部能力。
 */
interface VideoFileTranscodePort {
    /** 根据上传会话ID解析分片临时目录（绝对路径） */
    fun resolveTempDir(uploadId: Long): File

    /**
     * 解析目标输出目录（绝对路径）与相对存储路径
     * relativePath 例：video/{customerId}/{videoId}/{fileIndex}
     */
    fun resolveTargetDir(customerId: Long, videoId: Long, fileIndex: Int): Pair<File, String>

    /** 在目标目录下创建/返回用于分片合并的临时 MP4 文件 */
    fun newMergedOutputFile(targetDir: File): File

    /** 将分片合并为单一 MP4 */
    fun mergeChunks(sourceDir: File, outputFile: File)

    /** 探测视频编码 */
    fun detectCodec(filePath: String): String

    /** HEVC(H.265) -> H.264 转码 */
    fun transcodeHevcToH264(outputFilePath: String, inputFilePath: String)

    /** 切片为 TS 并生成 m3u8 */
    fun sliceToHls(tsFolder: File, inputMp4: File)

    /** 获取视频时长（秒） */
    fun durationOf(videoFilePath: String): Int

    /** 计算目录大小（字节） */
    fun folderSize(folder: File): Long

    /** 清理分片源目录 */
    fun cleanupTempDir(sourceDir: File)

    /** 是否 HEVC 编码 */
    fun isHevc(codec: String): Boolean
}

