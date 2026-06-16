import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { DeploymentResponse, DeploymentService } from '../core/services/deployment.service';

@Component({
  selector: 'app-deployments-page',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './deployments.page.html',
  styleUrl: './deployments.page.scss'
})
export class DeploymentsPage implements OnInit {
  deployments: DeploymentResponse[] = [];
  isLoading = false;
  errorMessage = '';

  constructor(private deploymentService: DeploymentService) {}

  ngOnInit(): void {
    this.loadDeployments();
  }

  loadDeployments(): void {
    this.isLoading = true;

    this.deploymentService.getDeployments().subscribe({
      next: (data) => {
        this.deployments = data;
        this.isLoading = false;
      },
      error: () => {
        this.errorMessage = 'Could not load deployments.';
        this.isLoading = false;
      }
    });
  }

  markSuccess(id: string): void {
    this.deploymentService.updateStatus(id, 'SUCCESS').subscribe({
      next: () => this.loadDeployments(),
      error: () => this.errorMessage = 'Could not update deployment.'
    });
  }
}
