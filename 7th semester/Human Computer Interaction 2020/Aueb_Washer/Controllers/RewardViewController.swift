//
//  RewardViewController.swift
//  Aueb_Washer
//
//  Created by Konstantinos Nikoloutsos on 28/12/20.
//

import UIKit

class RewardViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    @IBAction func onReviewButtonClicked(_ sender: Any) {
        goToMainController()
    }
    @IBAction func onCancelButtonClicked(_ sender: Any) {
        goToMainController()
    }
    
    
    func goToMainController(){
        navigationController?.popViewController(animated: false)
    }
    
}
