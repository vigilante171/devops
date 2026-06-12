import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-not-found-page',
  standalone: true,
  imports: [RouterLink],
  template: `
    <main class="not-found">
      <div>
        <h1>404</h1>
        <h2>Page not found</h2>
        <p>The page you are looking for does not exist.</p>
        <a routerLink="/dashboard">Back to dashboard</a>
      </div>
    </main>
  `,
  styles: [`
    .not-found {
      min-height: 100vh;
      display: grid;
      place-items: center;
      background: #080b12;
      color: white;
      font-family: Inter, system-ui, sans-serif;
      text-align: center;
    }

    h1 {
      font-size: 100px;
      margin: 0;
      background: linear-gradient(135deg, #2563eb, #9333ea);
      -webkit-background-clip: text;
      color: transparent;
    }

    h2 {
      margin: 0 0 10px;
    }

    p {
      color: #94a3b8;
      margin-bottom: 24px;
    }

    a {
      color: white;
      text-decoration: none;
      padding: 13px 18px;
      border-radius: 15px;
      background: linear-gradient(135deg, #2563eb, #9333ea);
      font-weight: 800;
    }
  `]
})
export class NotFoundPage {}
