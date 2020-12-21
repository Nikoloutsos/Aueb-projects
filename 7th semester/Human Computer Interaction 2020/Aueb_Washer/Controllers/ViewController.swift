//
//  ViewController.swift
//  Aueb_Washer
//
//  Created by Konstantinos Nikoloutsos on 19/12/20.
//

import UIKit

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationController?.setNavigationBarHidden(true, animated: false)
        
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        appDelegate.playSoundWelcome()
    
        DispatchQueue.main.asyncAfter(deadline: .now() + 6.5){
            let storyboard = UIStoryboard(name: "Main", bundle: nil)
            let pathDisplayVC = storyboard.instantiateViewController(withIdentifier: "mainVC") as! MenuViewController
            self.navigationController?.pushViewController(pathDisplayVC, animated: true)
        }
    }

}

