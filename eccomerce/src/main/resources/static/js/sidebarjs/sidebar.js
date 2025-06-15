  function toggleSubMenu(event) {
    event.preventDefault();
    const li = $(event.target).closest("li");

    // Close other submenus if needed (optional)
    $(".menu-item").not(li).removeClass("open").find(".submenu").slideUp();

    li.toggleClass("open");
    li.find(".submenu").slideToggle();
  }

  function setLiActive(pathname = window.location.pathname) {
    pathname = pathname.split("?")[0]; // Remove query params

    $(".sidebar a.submenu-link").each(function () {
      const link = $(this);
      const href = link.attr("href");

      if (href && pathname === href) {
        link.addClass("active");

        const li = link.closest("li"); // <li> for the submenu link
        const parentLi = li.closest(".menu-item"); // Top-level menu item
        const submenu = parentLi.find(".submenu");

        // Expand the parent menu and show submenu
        parentLi.addClass("open");
        submenu.show();
      }
    });
  }

  $(document).ready(function () {
    setLiActive();
  });
  
  
  function updateDateTime() {
       const now = new Date();
       const options = {
         weekday: 'short', year: 'numeric', month: 'short',
         day: 'numeric', hour: '2-digit', minute: '2-digit'
       };
       document.getElementById('dateTime').textContent = now.toLocaleDateString('en-US', options);
     }

     updateDateTime();
     setInterval(updateDateTime, 60000);

	 
	 
	/* $('input[type="text"]').on('keypress', function (event) {
	   const char = String.fromCharCode(event.which);
	   if (!/^[a-zA-Z\s]$/.test(char)) {
	     event.preventDefault();
	   }
	 });

	 
	 
	 $('input[type="number"]').on('keypress', function (event) {
	   const char = String.fromCharCode(event.which);

	   // Allow digits
	   if (/^[0-9]$/.test(char)) {
	     return;
	   }

	   // Allow one dot if not already present
	   if (char === '.' && !$(this).val().includes('.')) {
	     return;
	   }

	   // Block everything else
	   event.preventDefault();
	 });
	 */
	 
	 $(document).ready(function() {
		$('select').select2({
		  theme: 'bootstrap4',
		  width: '100%',
		  dropdownParent: $(document.body)
		});

	  });

	  
	  (() => {
	    'use strict';
	    const forms = document.querySelectorAll('.needs-validation');
	    Array.from(forms).forEach(form => {
	      form.addEventListener('submit', event => {
	        if (!form.checkValidity()) {
	          event.preventDefault();
	          event.stopPropagation();
	        }
	        form.classList.add('was-validated');
	      }, false);
	    });
	  })();

	  
	  
	  function showToast(message, bg = 'danger') {
	       const toastEl = $('#customToast');
	       $('#toastMessage').text(message);
	       toastEl.removeClass().addClass(`toast align-items-center text-bg-${bg} border-0`);
	       const toast = new bootstrap.Toast(toastEl[0]);
	       toast.show();
	     }
		 
		 
		 document.addEventListener("DOMContentLoaded", function() {
		            const loader = document.getElementById("loader");
		            // Ensure minimum display time for UX
		            setTimeout(() => {
		                loader.style.opacity = "0";
		                setTimeout(() => {
		                    loader.classList.add("hidden");
		                }, 400);
		            }, 500); // Match your 2-second load time
		        });
