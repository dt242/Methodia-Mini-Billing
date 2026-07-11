const API_BASE_URL = 'http://localhost:8080/api/billing';

export async function generateInvoice(requestData) {
    const response = await fetch(`${API_BASE_URL}/invoice`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        // Превръщаме JavaScript обекта в JSON текст за бекенда
        body: JSON.stringify(requestData)
    });

    if (!response.ok) {
        throw new Error('Грешка при генериране на фактура. Провери дали данните са коректни.');
    }

    // Връщаме готовата фактура (вече превърната обратно в JavaScript обект)
    return response.json();
}