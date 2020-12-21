//
//  TimeExtensions.swift
//  Aueb_Washer
//
//  Created by Konstantinos Nikoloutsos on 19/12/20.
//

import Foundation

func getNowReadableDate() -> String{
   let date = Date()
   let dayTimePeriodFormatter = DateFormatter()
   dayTimePeriodFormatter.dateFormat = "dd, MMMM yyyy"
   let dateString = dayTimePeriodFormatter.string(from: date)
   return "\(dateString)"
}

func getNowReadableTime() -> String{
    let date = Date()
    let dayTimePeriodFormatter = DateFormatter()
    dayTimePeriodFormatter.dateFormat = "HH:mm"
    let dateString = dayTimePeriodFormatter.string(from: date)
    return "\(dateString)"
}
