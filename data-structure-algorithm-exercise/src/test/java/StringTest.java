
/*
 * 创建人：baimiao
 * 创建时间：2023/7/17 11:12
 *
 */


import com.google.api.client.util.Lists;
import com.wifiin.common.JSON;

import java.util.List;
import java.util.Map;

public class StringTest {
    public static void main(String[] args) {
        String json = "{\"$AppRemoteConfigChanged\":[2538],\"$SignUp\":[2361,2650,2531,2477],\"GenerateOrder\":[2375],\"{profile}\":[2344,2654,2651,2638,2506,2629,2591,2601,2564,2582,2611,2574,2619,2547,2555,2532,2478],\"AlertPopupClick\":[2359,2501],\"ApiRequest\":[2337,2656,2643,2505,2634,2594,2625,2570,2607,2584,2580,2615,2550,2559,2535,2485],\"AutomaticDetectionNetwork\":[2356],\"$AppViewScreen\":[2340,2655,2652,2639,2504,2628,2620,2592,2573,2602,2565,2583,2610,2546,2556,2536,2480],\"NetworkProbeTask\":[2350],\"DataQuality001\":[2520],\"AdRequest\":[2345,2659,2642,2517,2622,2630,2585,2596,2567,2606,2613,2579,2545,2549,2557,2482],\"InstalledApps\":[2360,2641,2540,2488],\"EndConnectVPN\":[2349],\"AdResponse\":[2338,2658,2647,2518,2626,2632,2589,2599,2571,2605,2577,2616,2544,2558,2552,2484],\"StartConnectVPN\":[2357],\"GenerateOrderDetail\":[2368],\"$AppInstall\":[2358,2543,2481],\"$AppEnd\":[2343,2490,2512],\"IAPTransaction\":[2366],\"AppMsgOpened\":[2446],\"SpeedinAd\":[2363],\"ConfirmPayment\":[2367],\"AlertWords\":[2342,2649],\"PushPopup\":[2665],\"EnterPaymentPage\":[2371],\"ABTestInfo\":[2353,2511],\"LaunchpadClick\":[2351],\"profile\":[2644,2661,2653,2510,2635,2587,2627,2598,2572,2609,2581,2618,2551,2563,2533,2479],\"VipList\":[2636],\"$AppStart\":[2339,2637,2503,2600,2537],\"CheckNetwork\":[2662],\"DataQuality010\":[2664],\"AccountBind\":[2495],\"FailDownloadRulesFile\":[2354,2645,2660,2514,2631,2595,2624,2588,2603,2569,2578,2617,2561,2554,2539,2487],\"BuyGoodsDetail\":[2374],\"$AppClick\":[2346,2663,2648,2507,2623,2593,2590,2568,2604,2612,2575,2562,2553,2534,2489],\"FailRequestLine\":[2347,2646,2657,2513,2633,2621,2586,2597,2566,2608,2614,2576,2548,2541,2560,2486],\"$pageview\":[2470],\"LinePingCache\":[2355,2640,2508,2542,2483],\"AdShow\":[2348],\"CustomizedClick\":[2362],\"OperateRules\":[2370],\"DownloadTest\":[2365],\"AlertPopup\":[2352,2530]}";
        Map<String, Object> map = JSON.common().parse(json, Map.class);
        System.out.println(map.size());
        String lowerEnc = "vpnsdfsfds";
        List<String> removed = Lists.newArrayList();
        map.keySet().forEach(m -> {
            if (m.toLowerCase().contains(lowerEnc)) {

            } else {
//                removed.add(m);
            }
        });
        removed.forEach(map::remove);
        System.out.println(map);
    }
}
