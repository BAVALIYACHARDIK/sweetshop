import React, { useState } from 'react';

function Sweet() {
    const [query, setQuery] = useState('');
    const [isFilterOpen, setIsFilterOpen] = useState(false);
    const [theme, setTheme] = useState('all');
    const [specific, setSpecific] = useState('');

    return (
        <div className="sweet-page">
            <header className="sweet-header">
                <h1>Sweet Shop</h1>
            </header>

            <main className="sweet-body">
                <div className="search-row">
                    <div className="search-input">
                        <input
                            type="search"
                            placeholder="Search sweets, flavours, categories..."
                            value={query}
                            onChange={(e) => setQuery(e.target.value)}
                            aria-label="Search"
                        />
                    </div>

                    <div className="search-actions">
                        <button className="btn-primary" onClick={() => {/* implement search action */}}>Search</button>
                        <button className="button-filter" onClick={() => setIsFilterOpen(true)}>Filter</button>
                    </div>
                </div>

                <section className="results">
                    <p>Results will appear here for: <strong>{query}</strong></p>
                </section>
            </main>

            {isFilterOpen && (
                <div className="modal-backdrop" role="dialog" aria-modal="true">
                    <div className="modal">
                        <h3>Filter Options</h3>
                        <div className="form-group">
                            <label>Theme</label>
                            <select value={theme} onChange={(e) => setTheme(e.target.value)}>
                                <option value="all">All</option>
                                <option value="chocolate">Chocolate</option>
                                <option value="fruity">Fruity</option>
                                <option value="seasonal">Seasonal</option>
                            </select>
                        </div>

                        <div className="form-group">
                            <label>Specific Search</label>
                            <input
                                type="text"
                                placeholder="e.g. 'strawberry', 'dark', 'almond'"
                                value={specific}
                                onChange={(e) => setSpecific(e.target.value)}
                            />
                        </div>

                        <div className="modal-actions">
                            <button className="btn-primary" onClick={() => {
                                // Apply filter: in real app you'd run the filtered search
                                setIsFilterOpen(false);
                            }}>Apply</button>
                            <button className="" onClick={() => setIsFilterOpen(false)}>Close</button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}

export default Sweet;
