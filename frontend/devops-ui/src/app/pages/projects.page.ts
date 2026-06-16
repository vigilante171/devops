import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

import {
  CreateProjectRequest,
  DevOpsEnvironment,
  ProjectResponse,
  ProjectService,
  RepositoryConfig
} from '../core/services/project.service';

@Component({
  selector: 'app-projects-page',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './projects.page.html',
  styleUrl: './projects.page.scss'
})
export class ProjectsPage implements OnInit {
  projects: ProjectResponse[] = [];

  isLoading = false;
  isCreating = false;
  errorMessage = '';
  successMessage = '';

  name = 'Payment Platform';
  description = 'Microservices project managed by the DevOps Control Center.';
  provider = 'GITHUB';
  repositoryUrl = 'https://github.com/demo/payment-platform';
  defaultBranch = 'main';
  webhookEnabled = true;

  devUrl = 'http://dev.payment.local';
  stagingUrl = 'http://staging.payment.local';
  productionUrl = 'http://payment.local';

  constructor(
    private projectService: ProjectService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    setTimeout(() => this.loadProjects(), 100);
  }

  loadProjects(): void {
    this.isLoading = true;
    this.errorMessage = '';
    this.cdr.detectChanges();

    this.projectService.getProjects().subscribe({
      next: (data) => {
        this.projects = data;
        this.isLoading = false;
        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error('Load projects failed:', error);
        this.errorMessage = 'Could not load projects. Please check project-service and api-gateway.';
        this.isLoading = false;
        this.cdr.detectChanges();
      }
    });
  }

  createProject(): void {
    this.errorMessage = '';
    this.successMessage = '';

    if (!this.name.trim() || !this.repositoryUrl.trim()) {
      this.errorMessage = 'Project name and repository URL are required.';
      return;
    }

    this.isCreating = true;
    this.cdr.detectChanges();

    const repository: RepositoryConfig = {
      provider: this.provider,
      repositoryUrl: this.repositoryUrl,
      defaultBranch: this.defaultBranch || 'main',
      webhookEnabled: this.webhookEnabled
    };

    const environments: DevOpsEnvironment[] = [
      {
        name: 'Development',
        type: 'DEVELOPMENT',
        baseUrl: this.devUrl,
        protectedEnvironment: false
      },
      {
        name: 'Staging',
        type: 'STAGING',
        baseUrl: this.stagingUrl,
        protectedEnvironment: false
      },
      {
        name: 'Production',
        type: 'PRODUCTION',
        baseUrl: this.productionUrl,
        protectedEnvironment: true
      }
    ];

    const request: CreateProjectRequest = {
      name: this.name,
      description: this.description,
      repository,
      environments
    };

    this.projectService.createProject(request).subscribe({
      next: (createdProject) => {
        this.successMessage = 'Project created successfully.';
        this.projects = [createdProject, ...this.projects];
        this.isCreating = false;
        this.cdr.detectChanges();

        setTimeout(() => this.loadProjects(), 300);
      },
      error: (error) => {
        console.error('Create project failed:', error);
        this.errorMessage = 'Could not create project. Please verify the backend is running.';
        this.isCreating = false;
        this.cdr.detectChanges();
      }
    });
  }

  deleteProject(id: string): void {
    const confirmed = confirm('Delete this project?');
    if (!confirmed) {
      return;
    }

    this.projectService.deleteProject(id).subscribe({
      next: () => {
        this.successMessage = 'Project deleted successfully.';
        this.projects = this.projects.filter((project) => project.id !== id);
        this.cdr.detectChanges();

        setTimeout(() => this.loadProjects(), 300);
      },
      error: (error) => {
        console.error('Delete project failed:', error);
        this.errorMessage = 'Could not delete project.';
        this.cdr.detectChanges();
      }
    });
  }
}
