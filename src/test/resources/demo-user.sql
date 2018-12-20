/*
 Navicat Premium Data Transfer

 Source Server         : party
 Source Server Type    : Oracle
 Source Server Version : 110200
 Source Host           : 192.168.1.97:1521
 Source Schema         : PARTY

 Target Server Type    : Oracle
 Target Server Version : 110200
 File Encoding         : 65001

 Date: 19/06/2018 17:25:49
*/


-- ----------------------------
-- Table structure for TEST_TABLE
-- ----------------------------
DROP TABLE "PARTY"."TEST_TABLE";
CREATE TABLE "PARTY"."TEST_TABLE" (
  "UUID" VARCHAR2(32 BYTE) NOT NULL ,
  "TIME_TEMP" TIMESTAMP(6) 
)
TABLESPACE "party"
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;

-- ----------------------------
-- Primary Key structure for table TEST_TABLE
-- ----------------------------
ALTER TABLE "PARTY"."TEST_TABLE" ADD CONSTRAINT "SYS_C0014045" PRIMARY KEY ("UUID");

-- ----------------------------
-- Checks structure for table TEST_TABLE
-- ----------------------------
ALTER TABLE "PARTY"."TEST_TABLE" ADD CONSTRAINT "SYS_C0014044" CHECK ("UUID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;