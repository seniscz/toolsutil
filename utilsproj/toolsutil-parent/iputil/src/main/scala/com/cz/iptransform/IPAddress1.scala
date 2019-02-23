package com.cz.iptransform

import java.io.File
import java.net.InetAddress

import com.maxmind.geoip2.DatabaseReader
import com.maxmind.geoip2.model.CityResponse
import org.slf4j.LoggerFactory

import scala.collection.mutable

/**
  * @Description Convert IP to physical address
  * @Author chezhao
  * @datae 2019/2/22
  */
object IPAddress1 {
  private val logger = LoggerFactory.getLogger(IPAddress1.getClass)

  /**
    * @Description Convert IP to physical address (通过 GeoIP2 GeoLite2开源免费的数据库 解析)
    * @param ip   IP that needs to be converted
    * @param path The path to the GeoLite2-City.mmdb
    * @return map map("key","value")   map 的 value 为  中文 和 英文 拼接而成  eg: "江苏#Jiangsu"
    */
  def getAddress(ip: String, path: String): mutable.HashMap[String, Any] = {
    val map = new mutable.HashMap[String, Any]()
    val database = new File(path)
    val reader = new DatabaseReader.Builder(database).build()
    val ipAddress = InetAddress.getByName(ip)
    var response: CityResponse = null
    if (ip.substring(0, 7).equals("192.168")) {
      map.put("country", "局域网" + "#" + "juyuwang")
      map.put("province", "局域网" + "#" + "juyuwang")
      map.put("city", "局域网" + "#" + "juyuwang")
    }
    try {
      response = reader.city(ipAddress)
      val countries = response.getCountry()
      val country = countries.getNames.get("zh-CN")
      val countryE = countries.getName
      val subdivision = response.getMostSpecificSubdivision
      val province = subdivision.getNames.get("zh-CN")
      val provinceE = subdivision.getName
      val cities = response.getCity
      val city = cities.getNames.get("zh-CN")
      val cityE = cities.getName
      val location = response.getLocation
      val latitude = location.getLatitude
      val longitude = location.getLongitude
      if (country != null) {
        map.put("country", country + "#" + countryE)
      } else {
        map.put("country", "")
        logger.warn("this  " + ip + "country is null ")
      }
      if (province != null) {
        map.put("province", province + "#" + provinceE)
      } else {
        map.put("province", "")
        logger.warn("this  " + ip + "province is null ")
      }
      if (city != null) {
        map.put("city", city + "#" + cityE)
      } else {
        map.put("city", "")
        logger.warn("this  " + ip + "city is null ")
      }
      if (latitude != null) {
        map.put("latitude", latitude)
      } else {
        map.put("latitude", "")
        logger.warn("this  " + ip + "latitude is null ")
      }
      if (longitude != null) {
        map.put("this  " + ip + "longitude", longitude)
      } else {
        map.put("longitude", "")
        logger.warn("this  " + ip + "longitude is null ")
      }
    } catch {
      case ex: Exception => {
        logger.error("解析失败，该IP在database中不存在" + "this address" + ip + " is not in the database.")
      }
    }
    return map
  }

  def main(args: Array[String]): Unit = {
    val path = "F:\\GeoLite2-City.mmdb"
    val ip = "61.160.22.30"
    val map = getAddress(ip, path)
    if (map.nonEmpty) {
      val country = map.get("country").get.toString
      val province = map.get("province").get.toString
      val city = map.get("city").get.toString
      if (country.nonEmpty) {
        println("国家的中文值为： " + map.get("country").get.toString.split("#")(0))
        println("国家的英文值为： " + map.get("country").get.toString.split("#")(1))
      }
      if (province.nonEmpty) {
        println("省份的中文值为： " + map.get("province").get.toString.split("#")(0))
        println("省份的英文值为： " + map.get("province").get.toString.split("#")(1))
      }
      if (city.nonEmpty) {
        println("城市的中文值为:  " + map.get("city").get.toString.split("#")(0))
        println("城市的英文值为:  " + map.get("city").get.toString.split("#")(1))
      }
    } else {
      println("===========================")
    }
  }
}
