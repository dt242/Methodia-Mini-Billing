import { useState, useEffect } from 'react';
import './App.css';
import { generateInvoice, fetchInvoices } from './services/billingApi';

function App() {
  const [reference, setReference] = useState('123456789');
  const [year, setYear] = useState(2025);
  const [month, setMonth] = useState(3);
  const [product, setProduct] = useState('gas');
  const [invoices, setInvoices] = useState([]);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);
  const [initialLoad, setInitialLoad] = useState(true);

  useEffect(() => {
    const loadHistory = async () => {
      try {
        const data = await fetchInvoices();
        setInvoices(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setInitialLoad(false);
      }
    };

    loadHistory();
  }, []);

  const handleGenerate = async () => {
    setLoading(true);
    setError(null);

    try {
      const newInvoice = await generateInvoice({
        reference: reference,
        year: parseInt(year, 10),
        month: parseInt(month, 10),
        product: product
      });

      setInvoices((prevInvoices) => [...prevInvoices, newInvoice]);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container">
      <h1 className="title">Mini Billing System</h1>

      <div className="form-card">
        <h2 className="form-title">Генериране на фактура</h2>

        <div className="form-group">
          <div className="input-wrapper">
            <label>Референтен номер:</label>
            <input type="text" className="input-field" value={reference} onChange={(e) => setReference(e.target.value)} />
          </div>

          <div className="input-wrapper">
            <label>Година:</label>
            <input type="number" className="input-field" value={year} onChange={(e) => setYear(e.target.value)} />
          </div>

          <div className="input-wrapper">
            <label>Месец (1-12):</label>
            <input type="number" className="input-field" value={month} onChange={(e) => setMonth(e.target.value)} />
          </div>

          <div className="input-wrapper">
            <label>Продукт:</label>
            <select className="input-field" value={product} onChange={(e) => setProduct(e.target.value)}>
              <option value="gas">Газ (gas)</option>
              <option value="elec">Ток (elec)</option>
            </select>
          </div>

          <button type="button" className="submit-btn" onClick={handleGenerate} disabled={loading}>
            {loading ? 'Генериране...' : 'Генерирай фактура'}
          </button>
        </div>
      </div>

      <div>
        <h3 className="history-title">История на фактурите</h3>
        <hr className="divider" />

        {error && <p className="error-text">{error}</p>}
        {initialLoad && <p className="empty-text">Зареждане на историята...</p>}
        {!initialLoad && invoices.length === 0 && !error && <p className="empty-text">Няма генерирани фактури...</p>}

        <div style={{ marginTop: '15px' }}>
          {invoices.map((inv) => (
            <div key={inv.documentNumber} className="invoice-card">
              <h4 className="invoice-header">Фактура № {inv.documentNumber}</h4>
              <p className="invoice-detail"><strong>Клиент:</strong> {inv.consumer}</p>
              <p className="invoice-detail"><strong>Сума:</strong> {inv.totalAmount} лв.</p>
              <p className="invoice-detail"><strong>Дата:</strong> {new Date(inv.documentDate).toLocaleDateString('bg-BG')}</p>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default App;