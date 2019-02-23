package com.cz.iptransform

import com.cz.iputil.IPLocationUtil
import org.slf4j.LoggerFactory

import scala.collection.mutable

/**
  * @Description Convert IP to physical address
  * @Author chezhao
  * @datae 2019/2/22
  */
object IPAddress2 {
  private val logger = LoggerFactory.getLogger(IPAddress2.getClass)

  /**
    * @Description Convert IP to physical address (通过 纯真IP数据库 解析)
    * @param ip   IP that needs to be converted
    * @param path The path to the qqwry.dat
    * @return map("key","value")
    */
  def getAddress(ip: String, path: String): mutable.HashMap[String, String] = {
    val ipUtile = new IPLocationUtil(path)
    ipUtile.init()
    val country = ipUtile.getIPLocation(ip).getCountry()
    val province = ipUtile.getIPLocation(ip).getProvince()
    val city = ipUtile.getIPLocation(ip).getCity()
    val area = ipUtile.getIPLocation(ip).getArea()
    val address = ipUtile.getIPLocation(ip).getAddress()
    val map = new mutable.HashMap[String, String]()
    if (country != null) {
      map.put("country", country)
    } else {
      map.put("country", "")
      logger.warn("this  " + ip + "country is null ")
    }
    if (province != null) {
      map.put("province", province)
    } else {
      map.put("province", "")
      logger.warn("this  " + ip + "province is null ")
    }
    if (city != null) {
      map.put("city", city)
    } else {
      map.put("city", "")
      logger.warn("this  " + ip + "city is null ")
    }
    if (area != null) {
      map.put("area", area)
    } else {
      map.put("area", "")
      logger.warn("this  " + ip + "area is null ")
    }
    if (address != null) {
      map.put("address", address)
    } else {
      map.put("address", "")
      logger.warn("this  " + ip + "address is null ")
    }
    return map
  }

  def main(args: Array[String]): Unit = {
    val map = IPAddress2.getAddress("192.168.42.189", "F:\\qqwry.dat")
    println("country : " + map.get("country").get)
    println("province : " + map.get("province").get)
    println("city : " + map.get("city").get)
    println("area : " + map.get("area").get)
    println("address : " + map.get("address").get)
  }
}
