//
//  DuringWashingViewController.swift
//  DishWasher
//
//  Created by Konstantinos Nikoloutsos on 17/12/20.
//

import UIKit

class DuringWashingViewController: UIViewController {


    @IBOutlet weak var descLabel: UILabel!
    @IBOutlet weak var dishImage: UIImageView!
    @IBOutlet weak var progressBar: UIProgressView!
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var actionButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        appDelegate.playSoundWashStarted()
        
        var transform : CGAffineTransform = CGAffineTransform(scaleX: 1.0, y: 6.0)
        progressBar.transform = transform
        
        progressBar.setProgress(0, animated: false)
        
        Timer.scheduledTimer(timeInterval: 1.0, target: self, selector: #selector(self.doTick), userInfo: nil, repeats: true)
    }
    
    @IBAction func onStopButtonPressed(_ sender: Any) {
        
        if progressBar.progress < 1{
            let storyboard = UIStoryboard(name: "Main", bundle: nil)
            let infoVC = storyboard.instantiateViewController(withIdentifier: "AbortWashingVC") as! AbortWashingViewController
            self.navigationController?.pushViewController(infoVC, animated: true)
        }else{
            navigationController?.popViewController(animated: true)
        }
       
    }
    
    @objc func doTick(){
        progressBar.setProgress(progressBar.progress + 0.1, animated: true)
        
        
        if progressBar.progress >= 1.0 {
            let image = UIImage(named: "dishwashdone")
            dishImage.image = image
            titleLabel.text = "Το πλυντήριο τελείωσε"
            
            descLabel.text = "Μπορείτε να ανοίξετε το πλυντήριο"
            
            actionButton.setTitle("Εντάξει", for: .normal)
            actionButton.layer.backgroundColor = .init(red: 109.0/255.0, green: 212.0/255.0, blue: 0, alpha: 1)
            
        }
    }
}
