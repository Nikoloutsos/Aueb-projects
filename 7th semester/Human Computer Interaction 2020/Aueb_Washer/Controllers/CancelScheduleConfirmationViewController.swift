//
//  CancelScheduleConfirmationViewController.swift
//  DishWasher
//
//  Created by Konstantinos Nikoloutsos on 16/12/20.
//

import UIKit

class CancelScheduleConfirmationViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    @IBAction func onCancelButtonPressed(_ sender: Any) {
        goToMainController()
    }
    
    @IBAction func onBackButtonPressed(_ sender: Any) {
        navigationController?.popViewController(animated: true)
    }
    
    func goToMainController(){
        navigationController?.viewControllers.removeAll{
            (vc) -> Bool in
                if vc.isKind(of: MenuViewController.self) {
                    return false
                } else {
                    return true
                }
        }
    }
}
