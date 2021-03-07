//
//  InfoViewController.swift
//  Aueb_Washer
//
//  Created by Konstantinos Nikoloutsos on 19/12/20.
//

import UIKit

class InfoViewController: UIViewController {

    @IBOutlet weak var box1: UIView!
    @IBOutlet weak var box2: UIView!
    @IBOutlet weak var box3: UIView!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        title = "Πληροφορίες Πλυσιμάτων"
        self.navigationController?.setNavigationBarHidden(false, animated: false)
        
        box1.layer.cornerRadius = 12
        
        
        box2.layer.cornerRadius = 12
        
        
        box3.layer.cornerRadius = 12
       
        
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        let infoVC = storyboard.instantiateViewController(withIdentifier: "reviewVC") as! RewardViewController

        self.navigationController?.pushViewController(infoVC, animated: true)

    }
    

   

}
