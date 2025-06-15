const barChartCtx = document.getElementById('barChart').getContext('2d');
   const barChart = new Chart(barChartCtx, {
     type: 'bar',
     data: {
       labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May'],
       datasets: [{
         label: 'Sales ($)',
         data: [12000, 15000, 11000, 18000, 14000],
         backgroundColor: '#007bff'
       }, {
         label: 'Stock Remaining',
         data: [1200, 1150, 1000, 950, 1350],
         backgroundColor: '#28a745'
       }]
     },
     options: {
       responsive: true,
       plugins: {
         legend: { position: 'bottom' }
       }
     }
   });

   const pieChartCtx = document.getElementById('pieChart').getContext('2d');
   const pieChart = new Chart(pieChartCtx, {
     type: 'pie',
     data: {
       labels: ['Pending Orders', 'Completed Orders'],
       datasets: [{
         data: [pendingOrders, deliveredOrder],
         backgroundColor: ['#ffc107', '#28a745']
       }]
     },
     options: {
       responsive: true,
       plugins: {
         legend: { position: 'top' }
       }
     }
   });

   

  
