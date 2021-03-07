//
//  WaitingScheduledViewController.swift
//  DishWasher
//
//  Created by Konstantinos Nikoloutsos on 18/12/20.
//

import UIKit

class WaitingScheduledViewController: UIViewController {
    var washingType : WashingType!
    var scheduledTime : String = ""
    
    @IBOutlet weak var descLabel: UILabel!
    @IBOutlet weak var titleLabel: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        appDelegate.playSoundWashScheduled()
    
        titleLabel.text = "Προγραμματισμένο \(washingType.rawValue)"
        descLabel.text = "Το πλύσιμο των πιάτων θα ξεκινήσει στις \(scheduledTime)"
    }
    
    
    @IBAction func onCancelButtonPressed(_ sender: Any) {
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        let infoVC = storyboard.instantiateViewController(withIdentifier: "cancelScheduleVC") as! CancelScheduleConfirmationViewController
        self.navigationController?.pushViewController(infoVC, animated: true)
    }
    
    
    @IBAction func onTimeChangeButtonPressed(_ sender: Any) {
        navigationController?.popViewController(animated: true)
    }
}
