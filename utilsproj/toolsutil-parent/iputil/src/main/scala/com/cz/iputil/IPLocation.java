package com.cz.iputil;

/**
 * @Description Convert IP to physical address
 * @Author chezhao
 * @datae 2019/2/22
 */
public class IPLocation {

    /**
     * 区域 - 省份 + 城市
     */
    private String country;
    /**
     * 运营商
     */
    private String area;

    public IPLocation() {
        country = area = "";
    }

    public synchronized IPLocation getCopy() {
        IPLocation ret = new IPLocation();
        ret.country = country;
        ret.area = area;
        return ret;
    }

    public String getCountry() {
        return country;
    }

    /**
     * 得到省份名
     *
     * @return
     */
    public String getProvince() {
        String province = "";
        if (country != null) {
            String[] array = country.split("省");
            if (array != null && array.length > 1) {
                province = array[0] + "省";
            } else {
                if (country.contains("北京")) {
                    province = "北京市";
                } else {
                    if (country.contains("上海")) {
                        province = "上海市";
                    } else {
                        if (country.contains("重庆")) {
                            province = "重庆市";
                        } else {
                            if (country.contains("天津")) {
                                province = "天津市";
                            } else {
                                if (country.contains("内蒙古")) {
                                    province = "内蒙古自治区";
                                } else {
                                    if (country.contains("广西")) {
                                        province = "广西壮族自治区";
                                    } else {
                                        if (country.contains("西藏")) {
                                            province = "西藏自治区";
                                        } else {
                                            if (country.contains("新疆")) {
                                                province = "新疆维吾尔自治区";
                                            } else {
                                                if (country.contains("宁夏")) {
                                                    province = "宁夏回族自治区";
                                                } else {
                                                    if (country.contains("香港")) {
                                                        province = "香港特别行政区";
                                                    } else {
                                                        if (country.contains("澳门")) {
                                                            province = "澳门特别行政区";
                                                        } else {
                                                            province = country;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return province;
    }

    /**
     * 得到城市名
     *
     * @return
     */
    public String getCity() {
        String city = "";
        if (country != null) {
            String[] array = country.split("省");
            if (array != null && array.length > 1) {
                String tmpCity = array[1];
                String[] tmpCityArray = tmpCity.split("市");
                if (tmpCityArray.length > 1) {
                    city = tmpCityArray[0] + "市";
                } else {
                    city = tmpCity;
                }
            } else {
                //处理各自治区情况 ，对于各自治区来说 country的值类似为： 广西梧州市蒙山县
                if (country.contains("市")) {
                    String[] tmpCityArray2 = country.split("市");
                    city = tmpCityArray2[0] + "市";
                } else {
                    city = country;
                }
            }
            if (city.length() > 3) {
                if (city.contains("内蒙古")) {
                    city = city.replace("内蒙古", "");
                }
                if (city.contains("广西")) {
                    city = city.replace("广西", "");
                }
                if (city.contains("西藏")) {
                    city = city.replace("西藏", "");
                }
                if (city.contains("新疆")) {
                    city = city.replace("新疆", "");
                }
                if (city.contains("宁夏")) {
                    city = city.replace("宁夏", "");
                }
            }
        }
        return city;
    }

    /**
     * 得到运营商名
     *
     * @return
     */
    public String getArea() {
        return area;
    }

    /**
     * 得到详细地址
     *
     * @return
     */
    public String getAddress() {
        String address = this.getCountry() + this.getArea();
        return address;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setArea(String area) {
        //如果为局域网，纯真IP地址库的地区会显示CZ88.NET,这里把它去掉
        if (area.trim().equals("CZ88.NET")) {
            this.area = "局域网";
        } else {
            this.area = area;
        }
    }

}
