package com.cz.iptransform

import java.util.Locale

import com.google.i18n.phonenumbers.geocoding.PhoneNumberOfflineGeocoder
import com.google.i18n.phonenumbers.{PhoneNumberToCarrierMapper, PhoneNumberUtil, Phonenumber}
import org.slf4j.LoggerFactory

import scala.collection.mutable

/**
  * @Description 通过手机号码查询归属的基础类
  * @Author chezhao
  * @datae 2019/2/22
  */
object IPAddress3 {

  private val LOGGER = LoggerFactory.getLogger(IPAddress3.getClass)
  private val PHONE_NUMBER_UTIL = PhoneNumberUtil.getInstance()
  private val CARRIER_MAPPER = PhoneNumberToCarrierMapper.getInstance()
  private val GEO_CODER = PhoneNumberOfflineGeocoder.getInstance()
  private val COUNTRY_CODE = 86

  /**
    * @Description: 根据国家代码和手机号  判断手机号是否有效
    * @param phoneNumber 手机号
    * @param countryCode 国家代码
    * @return
    */
  def checkPhoneNumber(phoneNumber: String): Boolean = {
    val phone = phoneNumber.toLong
    val pn = new Phonenumber.PhoneNumber()
    pn.setCountryCode(COUNTRY_CODE)
    pn.setNationalNumber(phone)
    return PHONE_NUMBER_UTIL.isValidNumber(pn)
  }

  /**
    * @Description: 根据国家代码和手机号  判断手机运营商
    * @param phoneNumber
    * @param countryCode
    * @return
    */
  def getCarrier(phoneNumber: String): String = {
    val phone = phoneNumber.toLong
    val pn = new Phonenumber.PhoneNumber()
    pn.setCountryCode(COUNTRY_CODE)
    pn.setNationalNumber(phone)

    val carrierEn = CARRIER_MAPPER.getNameForNumber(pn, Locale.ENGLISH)
    var carrierZh = ""
    carrierZh += GEO_CODER.getDescriptionForNumber(pn, Locale.CHINESE)
    carrierEn match {
      case "China Mobile" => carrierZh += "移动"
      case "China Unicom" => carrierZh += "联通"
      case "China Telecom" => carrierZh += "电信"
    }
    return carrierZh
  }

  /**
    * @Description: 根据国家代码和手机号查询手机归属地
    * @param phoneNumber
    * @param countryCode
    * @return
    */
  def getGeo(phoneNumber: String): String = {
    val phone = phoneNumber.toLong
    val pn = new Phonenumber.PhoneNumber()
    pn.setCountryCode(COUNTRY_CODE)
    pn.setNationalNumber(phone)
    return GEO_CODER.getDescriptionForNumber(pn, Locale.CHINESE)
  }

  /**
    * @Description: 根据国家代码和手机号查询手机归属地
    * @param phoneNumber
    * @param countryCode
    * @return map
    */
  def getGeoMap(phoneNumber: String): mutable.HashMap[String, String] = {
    val map = new mutable.HashMap[String, String]()
    val phone = phoneNumber.toLong
    val pn = new Phonenumber.PhoneNumber()
    pn.setCountryCode(COUNTRY_CODE)
    pn.setNationalNumber(phone)
    val str = GEO_CODER.getDescriptionForNumber(pn, Locale.CHINESE)
    val array = str.toArray
    val index = array.indexOf('省')
    var province = ""
    var city = ""
    for (i <- 0 to index) {
      province += array(i)
    }
    for (i <- index + 1 to array.length - 1) {
      city += array(i)
    }
    map.put("province", province)
    map.put("city", city)
    return map
  }

  def main(args: Array[String]): Unit = {
    val map = getGeoMap("15291989485")
    println(map.get("province").get)
    println(map.get("city").get)
  }
}

