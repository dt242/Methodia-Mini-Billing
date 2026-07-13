const API_BASE_URL = 'http://localhost:8080/api/billing';

export async function generateInvoice(requestData) {
    const response = await fetch(`${API_BASE_URL}/invoice`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(requestData)
    });

    if (!response.ok) {
        throw new Error('Грешка при генериране на фактура. Провери дали данните са коректни.');
    }

    return response.json();
}

export async function fetchInvoices() {
    const response = await fetch(`${API_BASE_URL}/invoices`);

    if (!response.ok) {
        throw new Error('Грешка при зареждане на историята на фактурите.');
    }

    return response.json();
}

export async function reloadServerCache() {
    const response = await fetch(`${API_BASE_URL}/reload-data`, {
        method: 'POST'
    });

    if (!response.ok) {
        throw new Error('Грешка при презареждане на кеша.');
    }

    return response.text();
}