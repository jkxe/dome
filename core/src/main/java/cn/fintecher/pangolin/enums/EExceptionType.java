package cn.fintecher.pangolin.enums;

/**
     * @Description 异常类型枚举类
     */
    public enum EExceptionType {
        UNDISTRIBUTED(1111, "新入催未分配"),
        STRATEGY_DIVISION_FAILED(1112, "策略分案失败"),
        STAGNATION_TOO_LONG(1113, "催收队列停滞时间过长");

        private Integer value;
        private String remark;

        EExceptionType(Integer value, String remark) {
            this.value = value;
            this.remark = remark;
        }

        public Integer getValue() {
            return value;
        }

        public String getRemark() {
            return remark;
        }
    }