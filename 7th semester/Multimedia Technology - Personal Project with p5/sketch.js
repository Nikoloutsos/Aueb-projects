let imgB, imgS, button, a = 0;

function setup() {
  //creation of the canvas and the image, paragraph and button elements
    createCanvas(windowWidth, windowHeight, WEBGL);
    imgB = loadImage('assets/rosco.jpg');
    imgS = loadImage('assets/miguel.jpg');
    p = createP('Lights Out!');
    p.position(width/2.1,height*0.08);
    btn = createButton('Light Toggler');
    btn.position(width/2.1,height*0.92);
    btn.mousePressed(lightToggle);
  }

  function draw() {
    //sets the color grade of the background
    background(30);

    let locX = mouseX - width / 2;
    let locY = mouseY - height / 2;
    let dirX = (mouseX / width - 0.5) * 2;
    let dirY = (mouseY / height - 0.5) * 2;

    //depending on the value of the variable a, that changes whenever the button is pressed, the conditional changes the type of lighting and sets the text of the paragraph accordingly
    switch(a) {
      case 0: 
        ambientLight(0);
        p.elt.innerText = "Lights Out!";
        break;
      case 1:
        directionalLight(250, 250, 250, -dirX, -dirY, -1);
        p.elt.innerText = "Directional Light";
        break;
      case 2:
        pointLight(255, 255, 255, locX, locY, 250);
        p.elt.innerText = "Point Light";
        break;
    }
    
    //creation of the first 3d shape, a box: sets its position, sets it to rotate on all axes, its size and image texture
    //push and pop stores these drawing style settings so that we can alter them later for other elements
    push();
    translate(-width / 4, 0, 0);
    rotateX(frameCount*0.05);
    rotateY(frameCount*0.01);
    rotateZ(frameCount*0.05);
    texture(imgB);
    box(height/3);
    pop();

    //creation of the second 3d shape, a sphere
    translate(width / 4, 0, 0);
    rotateY(-frameCount*0.05);
    texture(imgS);
    sphere(120);
    noStroke();
  }

  //runs when the window is resized, to place everything accordingly to the scale
  function windowResized() {
    resizeCanvas(windowWidth, windowHeight);
    btn.position(width/2.15,height*0.92);
    p.position(width/2.1,height*0.08);
  }

  //the function that cycles a from 0 to 2 
  function lightToggle() {
    if(a === 2){
      a = 0;
    } else {
      a += 1;
    }
  }