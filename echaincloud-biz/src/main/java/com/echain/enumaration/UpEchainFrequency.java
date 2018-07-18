package com.echain.enumaration;

public enum UpEchainFrequency {

    ALL("all", 1),
    YEAR("year", 2),
    MOUTH("mouth", 3),
    WEEK("week", 4),
    DAY("day", 5),
    HOUR("hour", 6);

    private String name;
    private Integer index;

    public String getName() {
        return name;
    }

    public Integer getIndex() {
        return index;
    }

    UpEchainFrequency(String name, int index) {
        this.name = name;
        this.index = index;
    }

    //返回报表类型
    public static UpEchainFrequency getUpEchainFrequency(int index) {
        switch (index) {
            case 1:
                return UpEchainFrequency.ALL;
            case 2:
                return UpEchainFrequency.YEAR;
            case 3:
                return UpEchainFrequency.MOUTH;
            case 4:
                return UpEchainFrequency.WEEK;
            case 5:
                return UpEchainFrequency.DAY;
            case 6:
                return UpEchainFrequency.HOUR;
            default:
                return UpEchainFrequency.ALL;
        }
    }

    /**
     * 判断name是否属于枚举类的值
     * @param name
     * @return
     */
    public static boolean isInclude(String name){
        boolean include = false;
        for (UpEchainFrequency e: UpEchainFrequency.values()){
            if(e.getName().equals(name)){
                return true;
            }
        }
        return include;
    }
}
