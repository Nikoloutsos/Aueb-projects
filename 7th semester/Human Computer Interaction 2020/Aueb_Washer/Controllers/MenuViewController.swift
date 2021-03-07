//
//  MenuViewController.swift
//  DishWasher
//
//  Created by Konstantinos Nikoloutsos on 15/12/20.
//

import UIKit
import AVFoundation


class MenuViewController: UIViewController {
    
    @IBOutlet weak var level1Button: UIButton!
    @IBOutlet weak var level2Button: UIButton!
    @IBOutlet weak var level3Button: UIButton!
    @IBOutlet weak var infoImage: UIImageView!
    @IBOutlet weak var timeSchedulerView: UIView!
    @IBOutlet weak var selectTimeView: UIView!
    @IBOutlet weak var timePicker: UIDatePicker!
    @IBOutlet weak var dateLabel: UILabel!
    @IBOutlet weak var timeLabel: UILabel!
    
    
    var player: AVAudioPlayer?
    
    var washingType : WashingType?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = "Αρχική οθόνη"
      
        timeSchedulerView.isHidden = true
        selectTimeView.isHidden = true
        
        level1Button.layer.cornerRadius = 18
        level1Button.clipsToBounds = true
        
        level1Button.layer.shadowOffset = CGSize(width: 0, height: 1)
        level1Button.layer.shadowColor = UIColor.lightGray.cgColor
        level1Button.layer.shadowOpacity = 0
        level1Button.layer.shadowRadius = 5
        level1Button.layer.masksToBounds = false
        
        level2Button.layer.cornerRadius = 18
        level2Button.clipsToBounds = true
        
        level2Button.layer.shadowOffset = CGSize(width: 0, height: 1)
        level2Button.layer.shadowColor = UIColor.lightGray.cgColor
        level2Button.layer.shadowOpacity = 0
        level2Button.layer.shadowRadius = 5
        level2Button.layer.masksToBounds = false
        
        
        level3Button.layer.cornerRadius = 18
        level3Button.clipsToBounds = true
        
        level3Button.layer.shadowOffset = CGSize(width: 0, height: 1)
        level3Button.layer.shadowColor = UIColor.lightGray.cgColor
        level3Button.layer.shadowOpacity = 0
        level3Button.layer.shadowRadius = 5
        level3Button.layer.masksToBounds = false
        
        
        
        
        infoImage.isUserInteractionEnabled = true
        let tapRecognizer = UITapGestureRecognizer(target: self, action: #selector(imageTapped))
        infoImage.addGestureRecognizer(tapRecognizer)
        
        
        
        Timer.scheduledTimer(timeInterval: 10, target: self, selector: #selector(self.updateTime), userInfo: nil, repeats: true)
        updateTime()
    }
    
    @objc func updateTime(){
        let time = getNowReadableTime()
        let date = getNowReadableDate()
        
        timeLabel.text = time
        dateLabel.text = date
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(true, animated: false)
    }
    
    @objc func imageTapped(recognizer: UITapGestureRecognizer) {
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        let infoVC = storyboard.instantiateViewController(withIdentifier: "infoVC") as! InfoViewController
        self.navigationController?.pushViewController(infoVC, animated: true)
    }
    
    @IBAction func onLevel1ButtonClicked(_ sender: Any) {
        level1Button.alpha = 1
        level2Button.alpha = 0.4
        level3Button.alpha = 0.4
        timeSchedulerView.isHidden = false
        
        level1Button.layer.shadowOpacity = 1
        level2Button.layer.shadowOpacity = 0
        level3Button.layer.shadowOpacity = 0
        
        washingType = .lowType

    }
    
    
    @IBAction func onLevel2ButtonClicked(_ sender: Any) {
        level1Button.alpha = 0.4
        level2Button.alpha = 1
        level3Button.alpha = 0.4
        timeSchedulerView.isHidden = false
        
        level1Button.layer.shadowOpacity = 0
        level2Button.layer.shadowOpacity = 1
        level3Button.layer.shadowOpacity = 0
        
        washingType = .mediumType
    }
    
    @IBAction func onLevel3ButtonClicked(_ sender: Any) {
        level1Button.alpha = 0.4
        level2Button.alpha = 0.4
        level3Button.alpha = 1
        timeSchedulerView.isHidden = false
        
        level1Button.layer.shadowOpacity = 0
        level2Button.layer.shadowOpacity = 0
        level3Button.layer.shadowOpacity = 1
        
        washingType = .HighType
    
    }
    
    @IBAction func onSetTimeButtonPressed(_ sender: Any) {
        selectTimeView.isHidden = false
    }
    
    
    @IBAction func onStartButtonPressed(_ sender: Any) {
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        let infoVC = storyboard.instantiateViewController(withIdentifier: "duringWashingVC") as! DuringWashingViewController
        self.navigationController?.pushViewController(infoVC, animated: true)
    }
    
    
    @IBAction func onCancelButtonPressed(_ sender: Any) {
        timeSchedulerView.isHidden = true
        level1Button.alpha = 1
        level2Button.alpha = 1
        level3Button.alpha = 1
        
        level1Button.layer.shadowOpacity = 0
        level2Button.layer.shadowOpacity = 0
        level3Button.layer.shadowOpacity = 0
    }
    
    @IBAction func onCancelButtonTimeSelectedPressed(_ sender: Any) {
        selectTimeView.isHidden = true
    }
    
    
    @IBAction func onTimeSelectedButtonPressed(_ sender: Any) {
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        let infoVC = storyboard.instantiateViewController(withIdentifier: "waitingScheduledVC") as! WaitingScheduledViewController
     
        
        let date = timePicker.date
        let dayTimePeriodFormatter = DateFormatter()
        dayTimePeriodFormatter.dateFormat = "HH:mm"
        let timeString = dayTimePeriodFormatter.string(from: date)
        
        infoVC.washingType = washingType
        infoVC.scheduledTime = timeString
        
        self.navigationController?.pushViewController(infoVC, animated: true)
    }
}
