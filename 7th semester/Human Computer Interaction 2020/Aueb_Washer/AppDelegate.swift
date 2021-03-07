//
//  AppDelegate.swift
//  Aueb_Washer
//
//  Created by Konstantinos Nikoloutsos on 19/12/20.
//

import UIKit
import AVFoundation

@main
class AppDelegate: UIResponder, UIApplicationDelegate {
    
    var audioPlayer : AVAudioPlayer?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
        return true
    }

    // MARK: UISceneSession Lifecycle

    func application(_ application: UIApplication, configurationForConnecting connectingSceneSession: UISceneSession, options: UIScene.ConnectionOptions) -> UISceneConfiguration {
        // Called when a new scene session is being created.
        // Use this method to select a configuration to create the new scene with.
        return UISceneConfiguration(name: "Default Configuration", sessionRole: connectingSceneSession.role)
    }

    func application(_ application: UIApplication, didDiscardSceneSessions sceneSessions: Set<UISceneSession>) {
        // Called when the user discards a scene session.
        // If any sessions were discarded while the application was not running, this will be called shortly after application:didFinishLaunchingWithOptions.
        // Use this method to release any resources that were specific to the discarded scenes, as they will not return.
    }
    
    
    func playSoundWelcome(){
       playSound(for: "welcome.wav")
    }
    
    func playSoundWashStopped(){
        playSound(for: "wash_stopped.wav")
    }
    
    func playSoundWashStarted(){
        playSound(for: "wash_started.wav")
    }
    
    func playSoundWashScheduled(){
        playSound(for: "wash_scheduled.wav")
    }
    
    
    private func playSound(for path: String){
        let path = Bundle.main.path(forResource: path, ofType:nil)!
        let url = URL(fileURLWithPath: path)

        do {
            audioPlayer = try AVAudioPlayer(contentsOf: url)
            audioPlayer?.play()
        } catch {
            // couldn't load file :(
        }
    }


}

