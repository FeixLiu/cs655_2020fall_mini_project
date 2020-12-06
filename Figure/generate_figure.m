test1=[0.849,0.686,0.486,1.157,0.389,0.928,0,0;
    0.316,0.315,0.351,0.379,0.174,0.166,0,0;
    0.326,0.290,0.454,0.253,0.396,0.330,0,0;
    0.276,0.286,0.969,0.341,0.400,0.293,0,0;
    0.406,0.282,0.329,0.309,0.262,0.208,0,0;
    0.274,0.252,0.420,0.461,0.335,0.244,0,0;
    0.284,0.260,0.282,0.239,0.294,0.443,0,0;
    0.303,0.281,0.422,0.348,0.232,0.347,0,0;]; %trans
test2=[0.001,0.006,0.004,0.002,0.002,0.007,0,0;
    12.526,0.000,0.000,0.001,0.000,0.000,0,0;
    24.744,7.597,0.001,0.000,0.000,0.000,0,0;
    38.276,11.449,5.827,0.00,0.001,0.001,0,0;
    51.481,18.900,8.935,4.575,0.00,0.001,0,0;
    63.734,23.742,11.131,7.10,3.348,0.001,0,0;
    74.420,28.294,16.380,9.055,5.733,2.615,0,0;
    87.008,34.844,19.705,10.733,7.486,4.102,0,0;]; %queue
test3=[25.366,25.832,26.763,25.616,24.685,25.462,0,0;
    25.395,24.418,23.712,24.828,24.077,24.051,0,0;
    25.340,24.390,24.790,24.559,23.142,23.066,0,0;
    25.819,23.834,24.378,23.323,24.110,22.587,0,0;
    25.846,24.660,23.895,24.485,23.021,23.499,0,0;
    25.814,24.452,24.144,22.992,22.792,20.801,0,0;
    25.396,23.774,24.783,23.842,24.216,22.260,0,0;
    25.225,24.606,24.525,23.962,22.526,20.419,0,0;]; %crack

figure;
core=bar3(test1,'red');
for i=1:length(core)
       zz=get(core(i),'Zdata');
           k=1;
           for j= 0:6:(6*length(core)-6)
               zz(j+1:j+6,:)=zz(j+1:j+6,:)+test2(k,i)+test3(k,i);
               k=k+1;
           end
       set(core(i),'Zdata',zz);
end
hold on
core=bar3(test2,'yellow');
hold off
for i=1:length(core)
       zz=get(core(i),'Zdata');
       k=1;
       for j= 0:6:(6*length(core)-6)
           zz(j+1:j+6,:)=zz(j+1:j+6,:)+test3(k,i);
           k=k+1;
       end
       set(core(i),'Zdata',zz);
end
hold on
core=bar3(test3,'green');
title('Influence of different #workers and #users on time')
xlabel('Number of workers');
ylabel('Number of users');
zlabel('Time(s)');
xlim([0.5,6.5]);
ylim([0.5,8.5]);
hold off

figure;
workers=[1,2,3,4,5,6];
line1=[26.216,26.524,27.253,26.775,25.076,26.397];
line2=[38.237,24.732,24.070,25.207,24.251,24.217];
line3=[50.409,32.278,25.244,24.812,23.538,23.396];
line4=[64.371,35.568,31.174,23.664,24.511,22.880];
line5=[77.734,43.842,33.159,29.369,23.284,23.708];
line6=[89.822,48.446,35.695,30.553,26.474,21.046];
line7=[100.10,52.329,41.444,33.136,30.243,25.318];
line8=[112.536,59.731,44.652,35.043,30.244,24.868];
hold on;
plot(workers,line1);
plot(workers,line2);
plot(workers,line3);
plot(workers,line4);
plot(workers,line5);
plot(workers,line6);
plot(workers,line7);
plot(workers,line8);
legend('1 request','2 requests','3 requests','4 requests','5 requests','6 requests','7 requests','8 requests');
title('Influence of #workers on the total time');
xlabel('Number of workers');
ylabel('Time(s)');
hold off;

figure;
req=[1,2,3,4,5,6,7,8];
line1=[26.216,38.237,50.409,64.371,77.734,89.822,100.10,112.536];
line2=[26.524,24.732,32.278,35.568,43.842,48.446,52.329,59.731];
line3=[27.253,24.070,25.244,31.174,33.159,35.695,41.444,44.652];
line4=[26.775,25.207,24.812,23.664,29.369,30.553,33.136,35.043];
line5=[25.076,24.251,23.538,24.511,23.284,26.474,30.243,30.244];
line6=[26.397,24.217,23.396,22.880,23.708,21.046,25.318,24.868];
hold on;
plot(req,line1);
plot(req,line2);
plot(req,line3);
plot(req,line4);
plot(req,line5);
plot(req,line6);
legend('1 worker','2 workers','3 workers','4 workers','5 workers','6 workers');
title('Influence of #requests on the total time');
xlabel('Number of requests');
ylabel('Time(s)');
hold off;

%%
figure;
time=[0.2,50.775,102.449,152.626,203.032,252.909,306.334,360.138,404.932,457.58,506.975,544.01,591.494,638.275,709.856,781.933,830.153,888.32,951.185,1005.154,1070.491,1117.867,1168.078,1229.637,1272.478];
plot([1:25],time,'marker','^');
xlim([1,25])
set(gca,'xticklabel','');
%set(gca,'xtick',[1:25]);
%set(gca,'xticklabel',{'aaaaa','','eeeee','','iiiii','','mmmmm','','qqqqq','','uuuuu','','yyyyy','','CCCCC','','GGGGG','','KKKKK','','QQQQQ','','UUUUU','','YYYYY'});
title('Influence of difficulty on the total time');
xlabel('Difficulty');
ylabel('Time(s)');



