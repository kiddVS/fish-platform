SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tbl_url_scan
-- ----------------------------
DROP TABLE IF EXISTS `tbl_url_scan`;
CREATE TABLE `tbl_url_scan`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `url_raw` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '原始url',
  `url_domain` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '域名',
  `url_param` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数',
  `url_keyword` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关键字',
  `injectble` tinyint(4) NULL DEFAULT 0 COMMENT '是否可注入：0否 1是',
  `mail_count` int(11) NULL DEFAULT NULL COMMENT '邮箱数量',
  `mobile_count` int(11) NULL DEFAULT NULL COMMENT '手机数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;