    package com.example.demo2.entity;
    import com.baomidou.mybatisplus.annotation.TableField;
    import com.baomidou.mybatisplus.annotation.TableName;

    @TableName("shopcar")
    public class Shopcar {

        @TableField(value = "id")
        private int id;

        @TableField(value = "goodsId")
        private int goodsId;

        @TableField(value = "isJoined")
        private int isJoined;

        @TableField(value = "num")
        private int num;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getIsJoined() {
            return isJoined;
        }

        public void setIsJoined(int isJoined) {
            this.isJoined = isJoined;
        }



        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }
    }
