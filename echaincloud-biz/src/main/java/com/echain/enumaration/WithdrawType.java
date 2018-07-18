package com.echain.enumaration;

public enum WithdrawType {

    UNCHECK("处理中", 0),
    SUCCESS("提现成功", 1),
    FAIL("提现失败", 2);


    private String name;
    private Integer index;

    public String getName() {
        return name;
    }

    public Integer getIndex() {
        return index;
    }

    WithdrawType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    //返回报表类型
    public static WithdrawType getWalletType(int index) {
        switch (index) {
            case 0:
                return WithdrawType.UNCHECK;
            case 1:
                return WithdrawType.SUCCESS;
            case 2:
                return WithdrawType.FAIL;
            default:
                return WithdrawType.UNCHECK;
        }
    }

    /**
     * 判断name是否属于枚举类的值
     *
     * @param name
     * @return
     */
    public static boolean isInclude(String name) {
        boolean include = false;
        for (WithdrawType e : WithdrawType.values()) {
            if (e.getName().equals(name)) {
                return true;
            }
        }
        return include;
    }

    /**
     * 判断name是否属于枚举类的值
     *
     * @param name
     * @return
     */
    public static WithdrawType getByName(String name) {
        for (WithdrawType e : WithdrawType.values()) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return WithdrawType.UNCHECK;
    }
}
