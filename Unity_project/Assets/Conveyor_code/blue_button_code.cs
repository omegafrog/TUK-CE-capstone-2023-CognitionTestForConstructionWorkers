using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class blue_button_code : MonoBehaviour
{
    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        button_push();
    }

    void button_push()
    {
        if (Conveyor_button.blue_button)
        {
            StartCoroutine(blue_buttonpress(1.0f));
        }
    }

    IEnumerator blue_buttonpress(float delay) //버튼 입력시 효과
    {
        transform.localPosition = new Vector3(0f, 0.4f, 0f);
        while (delay > 0.5f)
        {
            delay -= Time.deltaTime;
            yield return new WaitForFixedUpdate();
        }
        transform.localPosition = new Vector3(0f, 0.5f, 0f);
        Conveyor_button.blue_button = false;
    }
}
