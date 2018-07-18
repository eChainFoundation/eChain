package com.echain.enumaration;

public enum WalletType {

    ETH("ETH", 1),
    ECP("ECP", 2),
    EOS("EOS", 3);

    private String name;
    private Integer index;

    public String getName() {
        return name;
    }

    public Integer getIndex() {
        return index;
    }

    WalletType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    //返回报表类型
    public static WalletType getWalletType(int index) {
        switch (index) {
            case 1:
                return WalletType.ETH;
            case 2:
                return WalletType.ECP;
            case 3:
                return WalletType.EOS;
            default:
                return WalletType.ETH;
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
        for (WalletType e : WalletType.values()) {
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
    public static WalletType getByName(String name) {
        for (WalletType e : WalletType.values()) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return WalletType.ETH;
    }
}
