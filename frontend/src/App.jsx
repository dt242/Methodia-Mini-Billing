import './App.css'

function App() {
  return (
    <div className="container">
      <h1 className="title">Mini Billing System</h1>

      <div className="form-card">
        <h2 className="form-title">Генериране на фактура</h2>

        <div className="form-group">
          <div className="input-wrapper">
            <label>Референтен номер:</label>
            <input type="text" className="input-field" defaultValue="123456789" />
          </div>

          <div className="input-wrapper">
            <label>Година:</label>
            <input type="number" className="input-field" defaultValue="2025" />
          </div>

          <div className="input-wrapper">
            <label>Месец (1-12):</label>
            <input type="number" className="input-field" defaultValue="3" />
          </div>

          <div className="input-wrapper">
            <label>Продукт:</label>
            <select className="input-field" defaultValue="gas">
              <option value="gas">Газ (gas)</option>
              <option value="elec">Ток (elec)</option>
            </select>
          </div>

          <button type="button" className="submit-btn">
            Генерирай фактура
          </button>
        </div>
      </div>

      <div>
        <h3 className="history-title">История на фактурите</h3>
        <hr className="divider" />
        <p className="empty-text">Фактура 1, ...</p>
      </div>
    </div>
  )
}

export default App