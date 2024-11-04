$(document).ready(function() {
    $.ajax({
        url: '/admin/sales-data',
        method: 'GET',
        success: function(data) {
            console.log(data); // 데이터 확인
            drawSalesGraph(data);
        },
        error: function(xhr, status, error) {
            console.error('Error fetching sales data:', error);
        }
    });

    function drawSalesGraph(salesData) {
        const labels = salesData.map(item => item.paymentDate);
        const deposits = salesData.map(item => item.totalDeposit);
        const withdrawals = salesData.map(item => item.totalWithdrawal);

        const ctx = document.getElementById('salesChart').getContext('2d');
        const salesChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [
                    {
                        label: 'Total Deposit',
                        data: deposits,
                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                        borderColor: 'rgba(75, 192, 192, 1)',
                        borderWidth: 1
                    },
                    {
                        label: 'Total Withdrawal',
                        data: withdrawals,
                        backgroundColor: 'rgba(255, 99, 132, 0.2)',
                        borderColor: 'rgba(255, 99, 132, 1)',
                        borderWidth: 1
                    }
                ]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    }
});
