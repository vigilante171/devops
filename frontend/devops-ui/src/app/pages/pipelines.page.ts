import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

import { ProjectResponse, ProjectService } from '../core/services/project.service';
import {
  CreatePipelineRequest,
  PipelineResponse,
  PipelineRunResponse,
  PipelineService,
  PipelineStep
} from '../core/services/pipeline.service';

@Component({
  selector: 'app-pipelines-page',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './pipelines.page.html',
  styleUrl: './pipelines.page.scss'
})
export class PipelinesPage implements OnInit {
  projects: ProjectResponse[] = [];
  pipelines: PipelineResponse[] = [];
  runs: PipelineRunResponse[] = [];

  isLoading = false;
  isCreating = false;
  errorMessage = '';
  successMessage = '';

  selectedProjectId = '';
  name = 'backend-ci-cd';
  description = 'Build, test, scan and deploy pipeline.';
  branch = 'main';

  buildCommand = 'mvn clean package';
  testCommand = 'mvn test';
  scanCommand = 'dependency-check';
  deployCommand = 'deploy artifact';

  constructor(
    private projectService: ProjectService,
    private pipelineService: PipelineService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    setTimeout(() => this.loadData(), 100);
  }

  loadData(): void {
    this.isLoading = true;
    this.errorMessage = '';
    this.successMessage = '';
    this.cdr.detectChanges();

    this.projectService.getProjects().subscribe({
      next: (projects) => {
        this.projects = projects;

        if (!this.selectedProjectId && projects.length > 0) {
          this.selectedProjectId = projects[0].id;
        }

        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error('Load projects failed:', error);
        this.errorMessage = 'Could not load projects. Create a project first or check project-service.';
        this.cdr.detectChanges();
      }
    });

    this.pipelineService.getPipelines().subscribe({
      next: (pipelines) => {
        this.pipelines = pipelines;
        this.isLoading = false;
        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error('Load pipelines failed:', error);
        this.errorMessage = 'Could not load pipelines. Please check pipeline-service and api-gateway.';
        this.isLoading = false;
        this.cdr.detectChanges();
      }
    });

    this.pipelineService.getRuns().subscribe({
      next: (runs) => {
        this.runs = runs;
        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error('Load pipeline runs failed:', error);
      }
    });
  }

  createPipeline(): void {
    this.errorMessage = '';
    this.successMessage = '';

    if (!this.selectedProjectId) {
      this.errorMessage = 'Please select a project before creating a pipeline.';
      return;
    }

    if (!this.name.trim()) {
      this.errorMessage = 'Pipeline name is required.';
      return;
    }

    this.isCreating = true;
    this.cdr.detectChanges();

    const steps: PipelineStep[] = [
      {
        name: 'Checkout',
        command: 'git checkout ' + (this.branch || 'main'),
        orderIndex: 1
      },
      {
        name: 'Build',
        command: this.buildCommand,
        orderIndex: 2
      },
      {
        name: 'Test',
        command: this.testCommand,
        orderIndex: 3
      },
      {
        name: 'Security Scan',
        command: this.scanCommand,
        orderIndex: 4
      },
      {
        name: 'Deploy',
        command: this.deployCommand,
        orderIndex: 5
      }
    ];

    const request: CreatePipelineRequest = {
      projectId: this.selectedProjectId,
      name: this.name,
      description: this.description,
      branch: this.branch || 'main',
      steps
    };

    this.pipelineService.createPipeline(request).subscribe({
      next: (createdPipeline) => {
        this.successMessage = 'Pipeline created successfully.';
        this.pipelines = [createdPipeline, ...this.pipelines];
        this.isCreating = false;
        this.cdr.detectChanges();

        setTimeout(() => this.loadData(), 300);
      },
      error: (error) => {
        console.error('Create pipeline failed:', error);
        this.errorMessage = 'Could not create pipeline. Please verify pipeline-service is running.';
        this.isCreating = false;
        this.cdr.detectChanges();
      }
    });
  }

  runPipeline(pipeline: PipelineResponse): void {
    this.errorMessage = '';
    this.successMessage = '';

    this.pipelineService.runPipeline(pipeline.id, {
      branch: pipeline.branch || 'main',
      commitSha: 'ui-run-' + Date.now()
    }).subscribe({
      next: (run) => {
        this.successMessage = 'Pipeline run started successfully.';
        this.runs = [run, ...this.runs];
        this.cdr.detectChanges();

        setTimeout(() => this.loadData(), 300);
      },
      error: (error) => {
        console.error('Run pipeline failed:', error);
        this.errorMessage = 'Could not run pipeline.';
        this.cdr.detectChanges();
      }
    });
  }

  deletePipeline(id: string): void {
    const confirmed = confirm('Delete this pipeline?');
    if (!confirmed) {
      return;
    }

    this.pipelineService.deletePipeline(id).subscribe({
      next: () => {
        this.successMessage = 'Pipeline deleted successfully.';
        this.pipelines = this.pipelines.filter((pipeline) => pipeline.id !== id);
        this.cdr.detectChanges();

        setTimeout(() => this.loadData(), 300);
      },
      error: (error) => {
        console.error('Delete pipeline failed:', error);
        this.errorMessage = 'Could not delete pipeline.';
        this.cdr.detectChanges();
      }
    });
  }

  getProjectName(projectId: string): string {
    return this.projects.find((project) => project.id === projectId)?.name || 'Unknown project';
  }
}
