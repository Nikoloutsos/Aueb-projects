//
//  AbortWashingViewController.swift
//  DishWasher
//
//  Created by Konstantinos Nikoloutsos on 16/12/20.
//

import UIKit

class AbortWashingViewController: UIViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
   
    @IBAction func onBackButtonPressed(_ sender: Any) {
        navigationController?.popViewController(animated: true)
    }
    
    @IBAction func onAbortButtonPressed(_ sender: Any) {
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        appDelegate.playSoundWashStopped()
        goToMainController()
    }
    
    func goToMainController(){
        navigationController?.viewControllers.removeAll(where: { (vc) -> Bool in
            if vc.isKind(of: MenuViewController.self) {
                return false
            } else {
                return true
            }
        })
    }
}
