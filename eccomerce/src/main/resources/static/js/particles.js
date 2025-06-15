particlesJS("particles-js", {
	  particles: {
	    number: { value: 60, density: { enable: true, value_area: 800 } },
	    color: { value: "#198754" }, // Bootstrap green (more contrast)
	    shape: { type: "circle" },
	    opacity: {
	      value: 1.9, // Increased for visibility
	      random: false
	    },
	    size: {
	      value: 4,
	      random: true
	    },
	    line_linked: {
	      enable: true,
	      distance: 130,
	      color: "#198754",
	      opacity: 1.9, // More visible lines
	      width: 1.2
	    },
	    move: {
	      enable: true,
	      speed: 2,
	      direction: "none",
	      random: false,
	      straight: false,
	      out_mode: "out"
	    }
	  },
	  interactivity: {
	    detect_on: "canvas",
	    events: {
	      onhover: { enable: true, mode: "repulse" },
	      onclick: { enable: true, mode: "push" },
	      resize: true
	    },
	    modes: {
	      repulse: { distance: 100, duration: 0.4 },
	      push: { particles_nb: 4 }
	    }
	  },
	  retina_detect: true
	});
	
	
	
	/*  particle moving code*/
	
	/*  
	
	let lastMouseX = 0;
let moveDirection = 0;
let isMoving = false;

// Track mouse movement direction
document.addEventListener("mousemove", function (e) {
  if (e.clientX > lastMouseX) {
    moveDirection = 1;  // right
    isMoving = true;
  } else if (e.clientX < lastMouseX) {
    moveDirection = -1; // left
    isMoving = true;
  } else {
    isMoving = false;
  }
  lastMouseX = e.clientX;
});

// Initialize particles (same as before)
particlesJS("particles-js", {
  particles: {
    number: { value: 60, density: { enable: true, value_area: 800 } },
    color: { value: "#198754" },
    shape: { type: "circle" },
    opacity: { value: 0.8 },
    size: { value: 4, random: true },
    line_linked: {
      enable: true,
      distance: 130,
      color: "#198754",
      opacity: 1.0,
      width: 1.2
    },
    move: {
      enable: true,
      speed: 2,
      direction: "none",
      random: false,
      straight: false,
      out_mode: "out"
    }
  },
  interactivity: {
    detect_on: "canvas",
    events: {
      onhover: { enable: true, mode: "repulse" },
      onclick: { enable: true, mode: "push" },
      resize: true
    },
    modes: {
      repulse: { distance: 100, duration: 0.4 },
      push: { particles_nb: 4 }
    }
  },
  retina_detect: true
});

// Set particle velocity instantly based on mouse movement
function updateParticleVelocity() {
  if (window.pJSDom && window.pJSDom.length > 0) {
    const pJS = window.pJSDom[0].pJS;
    pJS.particles.array.forEach(p => {
      if (isMoving) {
        p.vx = moveDirection * 2; // instant speed (tweak speed here)
      } else {
        p.vx = 0;  // instantly stop horizontal movement
      }
    });
  }
  requestAnimationFrame(updateParticleVelocity);
}
updateParticleVelocity();
	
	*/